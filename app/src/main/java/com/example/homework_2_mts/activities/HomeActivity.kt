package com.example.homework_2_mts.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.FragmentTransitionSupport
import com.example.homework_2_mts.MainFragment
import com.example.homework_2_mts.ProfileFragment
import com.example.homework_2_mts.R
import com.example.homework_2_mts.adapters.MoviesRecyclerViewAdapter
import com.example.homework_2_mts.adapters.PopularNowRecyclerViewAdapter
import com.example.homework_2_mts.data.dto.MovieDto
import com.example.homework_2_mts.data.features.movies.MoviesDataSourceImpl
import com.example.homework_2_mts.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.helpers.MoviesCallbackDiffUtils
import com.example.homework_2_mts.adapters.items_decoration.GridSpacingItemDecoration
import com.example.homework_2_mts.adapters.items_decoration.SpacesItemDecoration
import com.example.homework_2_mts.models.MoviesModel
import com.example.homework_2_mts.models.PopularNowModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var someFragment: MainFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener {
            val fragment: Fragment
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

}
