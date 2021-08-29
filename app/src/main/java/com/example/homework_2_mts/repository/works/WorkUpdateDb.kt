package com.example.homework_2_mts.repository.works

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
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
import java.util.*

class WorkUpdateDb(
    context: Context,
    workerParameters: WorkerParameters
) :
    CoroutineWorker(context, workerParameters) {

    private val movieRepository = MovieRepositoryImpl(App.instance.apiService)
    private val genreRepository = GenreRepositoryImpl()
    private val updateDbDateRepository = UpdateDbDateRepositoryImpl()

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
        const val CHANNEL_NAME = "updateDbChannel"
        const val CHANNEL_DESCRIPTION = "updateDbDescriptionChannel"
        const val UPDATE_DB_ID: Long = 1
    }

    override suspend fun doWork(): Result {
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
        createNotificationChannel()
        // Создаём уведомление
        val builder = NotificationCompat.Builder(App.applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(App.applicationContext.getString(R.string.notification_update_db_title))
            .setContentText(App.applicationContext.getString(R.string.notification_update_db_description))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(App.applicationContext)) {
            notify(NOTIFICATION_ID, builder.build()) // посылаем уведомление
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                App.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}