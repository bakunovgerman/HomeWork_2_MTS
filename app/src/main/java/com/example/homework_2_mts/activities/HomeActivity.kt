package com.example.homework_2_mts.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.items_decoration.GridSpacingItemDecoration
import com.example.homework_2_mts.items_decoration.SpacesItemDecoration
import com.example.homework_2_mts.adapters.MoviesRecyclerViewAdapter
import com.example.homework_2_mts.adapters.PopularNowRecyclerViewAdapter
import com.example.homework_2_mts.data.dto.MovieDto
import com.example.homework_2_mts.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.models.MoviesModel
import com.example.homework_2_mts.models.PopularNowModel
import com.example.homework_2_mts.data.features.movies.MoviesDataSourceImpl
import com.example.homework_2_mts.helpers.MoviesCallbackDiffUtils

class HomeActivity : AppCompatActivity() {
    private lateinit var popularNowModel: PopularNowModel
    private lateinit var moviesModel: MoviesModel
    private lateinit var popularNowRecyclerViewAdapter: PopularNowRecyclerViewAdapter
    private lateinit var moviesRecyclerViewAdapter: MoviesRecyclerViewAdapter
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var popularNowRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initView()
        setData()
    }

    private fun initView() {
        moviesRecyclerView = findViewById<RecyclerView>(R.id.rvMovies)
        popularNowRecyclerView = findViewById<RecyclerView>(R.id.rvPopularNow)
    }

    private fun setData() {
        popularNowModel = PopularNowModel(PopularNowDataSourceImpl())
        moviesModel = MoviesModel(MoviesDataSourceImpl())

        popularNowRecyclerViewAdapter  = PopularNowRecyclerViewAdapter(popularNowModel.getPopularNow())
        popularNowRecyclerViewAdapter.onPopularNowItemClick = { Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show() }

        moviesRecyclerViewAdapter = MoviesRecyclerViewAdapter(moviesModel.getMovies())
        moviesRecyclerViewAdapter.onMovieItemClick = {
            Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
        }

        popularNowRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularNowRecyclerViewAdapter
            addItemDecoration(SpacesItemDecoration(25))
        }

        moviesRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = moviesRecyclerViewAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 140, false))
        }
    }

    private fun changeData(oldList: List<MovieDto>, newList: List<MovieDto>){
        val callback = MoviesCallbackDiffUtils(oldList, newList)
        val diff = DiffUtil.calculateDiff(callback)
        diff.dispatchUpdatesTo(popularNowRecyclerViewAdapter)
        moviesRecyclerViewAdapter.list = newList
    }
}