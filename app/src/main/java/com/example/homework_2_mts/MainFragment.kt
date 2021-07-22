package com.example.homework_2_mts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.homework_2_mts.adapters.MoviesRecyclerViewAdapter
import com.example.homework_2_mts.adapters.PopularNowRecyclerViewAdapter
import com.example.homework_2_mts.adapters.items_decoration.GridSpacingItemDecoration
import com.example.homework_2_mts.adapters.items_decoration.SpacesItemDecoration
import com.example.homework_2_mts.data.dto.MovieDto
import com.example.homework_2_mts.data.features.movies.MoviesDataSourceImpl
import com.example.homework_2_mts.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.helpers.MoviesCallbackDiffUtils
import com.example.homework_2_mts.models.MoviesModel
import com.example.homework_2_mts.models.PopularNowModel


class MainFragment : Fragment() {

    private val popularNowModel: PopularNowModel = PopularNowModel(PopularNowDataSourceImpl())
    private val moviesModel: MoviesModel = MoviesModel(MoviesDataSourceImpl())
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var rvMovies: RecyclerView
    private lateinit var rvPopularNow: RecyclerView

    private lateinit var popularNowRecyclerViewAdapter: PopularNowRecyclerViewAdapter
    private lateinit var moviesRecyclerViewAdapter: MoviesRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        popularNowRecyclerViewAdapter =
            PopularNowRecyclerViewAdapter(popularNowModel.getPopularNow()) { Toast.makeText(view.context, it.name, Toast.LENGTH_SHORT).show() }
        moviesRecyclerViewAdapter =
            MoviesRecyclerViewAdapter(moviesModel.getMovies()) { Toast.makeText(view.context, it.title, Toast.LENGTH_SHORT).show() }

        initView(view)
        setData()
        initListener()

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {
                    // putString(ARG_PARAM1, param1)
                }
            }
    }

    private fun initView(view: View) {
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        rvMovies = view.findViewById(R.id.rvMovies)
        rvPopularNow = view.findViewById(R.id.rvPopularNow)
    }

    private fun initListener() {
        swipeRefresh.setOnRefreshListener {
            updateData(moviesModel.getMovies(), moviesModel.getMovies2())
        }
    }

    private fun setData() {
        rvPopularNow.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularNowRecyclerViewAdapter
            addItemDecoration(SpacesItemDecoration(25))
        }

        rvMovies.apply {
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