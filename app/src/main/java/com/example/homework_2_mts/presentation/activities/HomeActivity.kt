package com.example.homework_2_mts.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.homework_2_mts.R
import com.example.homework_2_mts.repository.database.entities.Movie
import com.example.homework_2_mts.repository.database.entities.Genre
import com.example.homework_2_mts.presentation.fragments.MovieDetailFragment
import com.example.homework_2_mts.presentation.helpers.MainFragmentClickListener
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity(), MainFragmentClickListener {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    companion object{
        const val MOVIE_KEY = "movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        navController = findNavController(R.id.nav_host)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    if (navController.currentDestination?.id == R.id.movieDetailFragment) {
                        navController.popBackStack(R.id.mainFragment, false)
                    } else if (navController.currentDestination?.id == R.id.profileFragment) {
                        navController.navigate(R.id.action_profile_fragment_to_main_fragment)
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
            || navController.previousBackStackEntry?.destination?.id == R.id.mainFragment) {

            bottomNavigationView.selectedItemId = R.id.menu_home

        } else {
            super.onBackPressed()
        }
    }

    override fun onOpenDetailMovieClick(movie: Movie) {
        val bundle = bundleOf(MovieDetailFragment.MOVIE_KEY to movie)
        navController.navigate(R.id.action_main_fragment_to_movie_detail_fragment, bundle)
    }

    override fun onPopularNowClick(genre: Genre) {
        Toast.makeText(this, genre.name, Toast.LENGTH_LONG).show()
    }

}
