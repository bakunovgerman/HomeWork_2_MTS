package com.example.homework_2_mts.activities

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ViewTreeObserver
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
import com.example.homework_2_mts.items_decoration.GridSpacingItemDecoration
import com.example.homework_2_mts.items_decoration.SpacesItemDecoration
import com.example.homework_2_mts.models.MoviesModel
import com.example.homework_2_mts.models.PopularNowModel


class HomeActivity : AppCompatActivity() {
    private lateinit var popularNowModel: PopularNowModel
    private lateinit var moviesModel: MoviesModel
    private lateinit var popularNowRecyclerViewAdapter: PopularNowRecyclerViewAdapter
    private lateinit var moviesRecyclerViewAdapter: MoviesRecyclerViewAdapter
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var popularNowRecyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initView()
        initListener()
        setData()
    }

    private fun initView() {
        moviesRecyclerView = findViewById<RecyclerView>(R.id.rvMovies)
        popularNowRecyclerView = findViewById<RecyclerView>(R.id.rvPopularNow)
        swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)
    }
    private fun initListener(){
        swipeRefresh.setOnRefreshListener {
            updateData(moviesModel.getMovies(), moviesModel.getMovies2())
        }
    }

    private fun setData() {
        popularNowModel = PopularNowModel(PopularNowDataSourceImpl())
        moviesModel = MoviesModel(MoviesDataSourceImpl())

        popularNowRecyclerViewAdapter  = PopularNowRecyclerViewAdapter(popularNowModel.getPopularNow()) {Unit}
        popularNowRecyclerViewAdapter.onPopularNowItemClick = { Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show() }

        moviesRecyclerViewAdapter = MoviesRecyclerViewAdapter(moviesModel.getMovies()) { Unit }
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
            addItemDecoration(GridSpacingItemDecoration(100))
        }
    }

    private fun updateData(oldList: List<MovieDto>, newList: List<MovieDto>){
        val callback = MoviesCallbackDiffUtils(oldList, newList)
        val diff = DiffUtil.calculateDiff(callback)
        diff.dispatchUpdatesTo(moviesRecyclerViewAdapter)
        moviesRecyclerViewAdapter.list = newList
        swipeRefresh.isRefreshing = false
    }
}