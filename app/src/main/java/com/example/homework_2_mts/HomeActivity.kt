package com.example.homework_2_mts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.adapters.GridSpacingItemDecoration
import com.example.homework_2_mts.adapters.SpacesItemDecoration
import com.example.homework_2_mts.adapters.MoviesRecyclerViewAdapter
import com.example.homework_2_mts.adapters.PopularNowRecyclerViewAdapter
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
        val popularNowRecyclerViewAdapter  = PopularNowRecyclerViewAdapter(popularNowModel.getPopularNow())
        popularNowRecyclerViewAdapter.onPopularNowItemClick = { Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show() }
        val moviesRecyclerViewAdapter = MoviesRecyclerViewAdapter(moviesModel.getMovies())
        moviesRecyclerViewAdapter.onMovieItemClick = {
            Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
        }

        findViewById<RecyclerView>(R.id.popularNowRecyclerView)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularNowRecyclerViewAdapter
            addItemDecoration(SpacesItemDecoration(25))
        }
        findViewById<RecyclerView>(R.id.moviesRecyclerView)?.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = moviesRecyclerViewAdapter
            addItemDecoration(GridSpacingItemDecoration())
        }
    }
}