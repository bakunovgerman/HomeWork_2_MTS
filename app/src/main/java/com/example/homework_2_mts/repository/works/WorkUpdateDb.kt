package com.example.homework_2_mts.repository.works

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.database.entities.UpdateDbDateEntity
import com.example.homework_2_mts.repository.mappers.MoviesMapper
import com.example.homework_2_mts.repository.repositories.GenreRepositoryImpl
import com.example.homework_2_mts.repository.repositories.MovieRepositoryImpl
import com.example.homework_2_mts.repository.repositories.UpdateDbDateRepositoryImpl
import com.example.homework_2_mts.repository.repositories.interfaces.GenreRepository
import com.example.homework_2_mts.repository.repositories.interfaces.MovieRepository
import com.example.homework_2_mts.repository.repositories.interfaces.UpdateDbDateRepository
import java.util.*

class WorkUpdateDb(
    context: Context,
    workerParameters: WorkerParameters
) :
    CoroutineWorker(context, workerParameters) {

    private val movieRepository: MovieRepository = MovieRepositoryImpl(App.instance.apiService)
    private val genreRepository: GenreRepository = GenreRepositoryImpl()
    private val updateDbDateRepository: UpdateDbDateRepository = UpdateDbDateRepositoryImpl()

    override suspend fun doWork(): Result {
        Log.d("workRequestState", "doWork()")
        return try {
            val responseMoviesApi = movieRepository.getMoviesAPI()
            if (responseMoviesApi.isSuccessful) {
                val movies = responseMoviesApi.body()?.moviesApiList ?: emptyList()
                if (movies.isNotEmpty()) {
                    movieRepository.insertDbMovies(MoviesMapper().toEntityList(movies))
                    genreRepository.insertDbGenres(genreRepository.getGenresAPI())
                    updateDbDateRepository.insertDate(UpdateDbDateEntity(UPDATE_DB_ID, Date().time))
                    showNotification()
                    Result.success()
                } else
                    Result.retry()
            } else
                Result.failure()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun showNotification() {
        Log.d("workRequestState", "showNotification зашел")
        Log.d("workRequestState", "applicationContext = $applicationContext")
        createNotificationChannel()
        // Создаём уведомление
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(App.applicationContext.getString(R.string.notification_update_db_title))
            .setContentText(applicationContext.getString(R.string.notification_update_db_description))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(applicationContext)) {
            Log.d("workRequestState", "notify build()")
            notify(NOTIFICATION_ID, builder.build()) // посылаем уведомление
        }
    }

    private fun createNotificationChannel() {
        Log.d("workRequestState", "createNotificationChannel start")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d("workRequestState", "createNotificationChannel create")
        }
    }

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
        const val CHANNEL_NAME = "updateDbChannel"
        const val CHANNEL_DESCRIPTION = "updateDbDescriptionChannel"
        const val UPDATE_DB_ID: Long = 1
    }

}