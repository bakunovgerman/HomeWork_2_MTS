package com.example.homework_2_mts.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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


class HomeActivity : AppCompatActivity() {

    private val popularNowModel: PopularNowModel = PopularNowModel(PopularNowDataSourceImpl())
    private val moviesModel: MoviesModel = MoviesModel(MoviesDataSourceImpl())
    private val swipeRefresh: SwipeRefreshLayout =
        findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
    private val popularNowRecyclerViewAdapter: PopularNowRecyclerViewAdapter =
        PopularNowRecyclerViewAdapter(popularNowModel.getPopularNow()) { Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show() }
    private val moviesRecyclerViewAdapter: MoviesRecyclerViewAdapter =
        MoviesRecyclerViewAdapter(moviesModel.getMovies()) { Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setData()
        initListener()
    }

    private fun initListener() {
        swipeRefresh.setOnRefreshListener {
            updateData(moviesModel.getMovies(), moviesModel.getMovies2())
        }
    }

    private fun setData() {
        findViewById<RecyclerView>(R.id.rvPopularNow).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularNowRecyclerViewAdapter
            addItemDecoration(SpacesItemDecoration(25))
        }

        findViewById<RecyclerView>(R.id.rvMovies).apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = moviesRecyclerViewAdapter
            addItemDecoration(GridSpacingItemDecoration(100))
        }
    }

    private fun updateData(oldList: List<MovieDto>, newList: List<MovieDto>) {
        val callback = MoviesCallbackDiffUtils(oldList, newList)
        val diff = DiffUtil.calculateDiff(callback)
        diff.dispatchUpdatesTo(moviesRecyclerViewAdapter)
        moviesRecyclerViewAdapter.list = newList
        swipeRefresh.isRefreshing = false
    }
}