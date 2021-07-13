package com.example.homework_2_mts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.adapters.SpacesItemDecoration
import com.example.homework_2_mts.adapters.moviesRecyclerViewAdapter
import com.example.homework_2_mts.adapters.popularNowRecyclerViewAdapter
import com.example.homework_2_mts.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.models.MoviesModel
import com.example.homework_2_mts.models.PopularNowModel
import com.example.homework_2_mts.data.features.movies.MoviesDataSourceImpl

class HomeActivity : AppCompatActivity() {
    private lateinit var popularNowModel: PopularNowModel
    private lateinit var moviesModel: MoviesModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        popularNowModel = PopularNowModel(PopularNowDataSourceImpl())
        moviesModel = MoviesModel(MoviesDataSourceImpl())
        findViewById<RecyclerView>(R.id.popularNowRecyclerView)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularNowRecyclerViewAdapter(popularNowModel.getPopularNow())
            addItemDecoration(SpacesItemDecoration(20))
        }
        findViewById<RecyclerView>(R.id.moviesRecyclerView)?.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = moviesRecyclerViewAdapter(moviesModel.getMovies())
            addItemDecoration(SpacesItemDecoration(spaceBottom = 100))
        }
    }
}