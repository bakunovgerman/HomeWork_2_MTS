package com.example.homework_2_mts.presentation.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.presentation.fragments.ActorDetailFragment
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.presentation.fragments.MovieDetailFragment
import com.example.homework_2_mts.presentation.helpers.MainFragmentClickListener
import com.example.homework_2_mts.repository.repositories.GenreRepositoryImpl
import com.example.homework_2_mts.repository.repositories.MovieRepositoryImpl
import com.example.homework_2_mts.repository.repositories.UpdateDbDateRepositoryImpl
import com.example.homework_2_mts.repository.works.WorkUpdateDb
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity(), MainFragmentClickListener {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // запускаю WorkManager
        initWorkManager()

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        navController = findNavController(R.id.nav_host)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    if (navController.currentDestination?.id in listOf(
                            R.id.movieDetailFragment,
                            R.id.profileFragment
                        )
                    ) {
                        navController.popBackStack(R.id.mainFragment, false)
                    }
                    true
                }
                R.id.menu_profile -> {
                    if (navController.currentDestination?.id == R.id.movieDetailFragment) {
                        navController.navigate(R.id.action_movie_detail_fragment_to_profile_fragment)
                    } else if (navController.currentDestination?.id == R.id.mainFragment) {
                        navController.navigate(R.id.action_main_fragment_to_profile_fragment)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.profileFragment
            && navController.previousBackStackEntry?.destination?.id == R.id.movieDetailFragment
            || navController.previousBackStackEntry?.destination?.id == R.id.mainFragment
        ) {

            bottomNavigationView.selectedItemId = R.id.menu_home

        } else {
            super.onBackPressed()
        }
    }

    override fun onOpenDetailMovieClick(movieEntity: MovieEntity) {
        val bundle = bundleOf(MovieDetailFragment.MOVIE_KEY to movieEntity)
        navController.navigate(R.id.action_main_fragment_to_movie_detail_fragment, bundle)
    }

    override fun onPopularNowClick(genreEntity: GenreEntity) {
        Toast.makeText(this, genreEntity.name, Toast.LENGTH_LONG).show()
    }

    override fun onOpenDetailActorClick(idActor: Long) {
        val bundle = bundleOf(ActorDetailFragment.ID_ACTOR to idActor)
        navController.navigate(R.id.action_movie_detail_fragment_to_actor_detail_fragment, bundle)
    }

    private fun initWorkManager() {
        // инит констраинтов
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        // инит переодичного запроса
        val workRequest: PeriodicWorkRequest =
            PeriodicWorkRequest.Builder(
                WorkUpdateDb::class.java,
                1,
                TimeUnit.DAYS
            )
                .setConstraints(constraint)
                    .build()
        val manager = WorkManager.getInstance(this)
        manager.getWorkInfoByIdLiveData(workRequest.id).observe(this, {
            Log.d("workRequestState", "Status: ${it.state}")
        })
        manager.enqueueUniquePeriodicWork(
            TAG_NAME_WORK_UPDATE_DB,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    override fun onResume() {
        super.onResume()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    companion object {
        const val MOVIE_KEY = "movie"
        const val TAG_NAME_WORK_UPDATE_DB = "updateDb"
    }

}
