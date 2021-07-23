package com.example.homework_2_mts.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.homework_2_mts.fragments.MainFragment
import com.example.homework_2_mts.fragments.ProfileFragment
import com.example.homework_2_mts.R
import com.example.homework_2_mts.data.dto.MovieDto
import com.example.homework_2_mts.data.dto.PopularNowDto
import com.example.homework_2_mts.fragments.MovieDetailFragment
import com.example.homework_2_mts.helpers.MainFragmentClickListener
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity(), MainFragmentClickListener {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var someFragment: MainFragment? = null
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_home -> {
                    fragment = MainFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_layout, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                    return@setOnItemSelectedListener true
                }
                else -> {
                    fragment = ProfileFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_layout, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                    return@setOnItemSelectedListener true
                }
            }
        }

        if (savedInstanceState == null) {
            someFragment = MainFragment.newInstance()
            someFragment?.apply {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_layout, this, "MainFragment")
                    .commit()
            }
        } else {
            someFragment =
                supportFragmentManager.findFragmentByTag("MainFragment") as? MainFragment
        }

    }
    private fun openDetailMovie(movie: MovieDto) {
        fragment = MovieDetailFragment.newInstance(movie)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layout, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }

    override fun onOpenDetailMovieClicked(movieDto: MovieDto) {
        openDetailMovie(movieDto)
    }

    override fun onClickPopularNow(popularNowDto: PopularNowDto) {
        Toast.makeText(this, popularNowDto.name, Toast.LENGTH_LONG).show()
    }

}
