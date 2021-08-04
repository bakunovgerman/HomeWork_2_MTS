package com.example.homework_2_mts.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.homework_2_mts.R
import com.example.homework_2_mts.adapters.MoviesAdapter
import com.example.homework_2_mts.adapters.PopularNowAdapter
import com.example.homework_2_mts.adapters.items_decoration.GridSpacingItemDecoration
import com.example.homework_2_mts.adapters.items_decoration.SpacesItemDecoration
import com.example.homework_2_mts.data.dto.MovieDto
import com.example.homework_2_mts.data.dto.PopularNowDto
import com.example.homework_2_mts.data.features.movies.MoviesDataSourceImpl
import com.example.homework_2_mts.data.features.popular.PopularNowDataSourceImpl
import com.example.homework_2_mts.helpers.MainFragmentClickListener
import com.example.homework_2_mts.helpers.MoviesCallbackDiffUtils
import com.example.homework_2_mts.models.MoviesModel
import com.example.homework_2_mts.models.PopularNowModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.Dispatcher
import java.lang.Exception


class MainFragment : Fragment() {

    private val errorHandler = CoroutineExceptionHandler { _, error ->
        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
    }

    private val popularNowModel: PopularNowModel = PopularNowModel(PopularNowDataSourceImpl())
    private val moviesModel: MoviesModel = MoviesModel(MoviesDataSourceImpl())
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var rvMovies: RecyclerView
    private lateinit var rvPopularNow: RecyclerView
    private lateinit var progressBar: FrameLayout
    private var mainFragmentClickListener: MainFragmentClickListener? = null

    private val popularNowList = popularNowModel.getPopularNow()

    private lateinit var popularNowAdapter: PopularNowAdapter
    private lateinit var moviesAdapter: MoviesAdapter

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        initView(view)
        initListener()
        initRv()
        setData()

        return view
    }

    private fun initRv() {
        popularNowAdapter =
            PopularNowAdapter() {
                mainFragmentClickListener?.onPopularNowClick(it)
            }
        moviesAdapter =
            MoviesAdapter() {
                mainFragmentClickListener?.onOpenDetailMovieClick(it)
            }

        rvPopularNow.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularNowAdapter
            addItemDecoration(SpacesItemDecoration(spaceRight = 6 ,spaceLeft = 20, size = popularNowList.size))
        }

        rvMovies.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = moviesAdapter
            addItemDecoration(GridSpacingItemDecoration(50))
        }
    }


    private fun initView(view: View) {
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        rvMovies = view.findViewById(R.id.rvMovies)
        rvPopularNow = view.findViewById(R.id.rvPopularNow)
        progressBar = view.findViewById(R.id.progressBar)
    }

    private fun initListener() {
        swipeRefresh.setOnRefreshListener {
            updateData(moviesModel.getMovies())
        }
    }

    @DelicateCoroutinesApi
    private fun setData() {
        CoroutineScope(Dispatchers.Main).launch(errorHandler) {

            val moviesList: List<MovieDto>
            val popularNowList: List<PopularNowDto>

            withContext(IO + errorHandler){
                Thread.sleep(2000)
                moviesList = moviesModel.getMovies()
                popularNowList = popularNowModel.getPopularNow()
            }

            moviesAdapter.movieList = moviesList
            popularNowAdapter.popularNowList = popularNowList

            hideProgressBar()
        }

    }

    private fun updateData(oldList: List<MovieDto>) {
        CoroutineScope(Dispatchers.Main).launch(errorHandler) {
            showProgressBar()

            val newList: List<MovieDto>

            withContext(IO + errorHandler){
                Thread.sleep(1500)
                newList = moviesModel.getMovies2()
            }

            val callback = MoviesCallbackDiffUtils(oldList, newList)
            val diff = DiffUtil.calculateDiff(callback)
            diff.dispatchUpdatesTo(moviesAdapter)
            moviesAdapter.movieList = newList
            swipeRefresh.isRefreshing = false

            hideProgressBar()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainFragmentClickListener)
            mainFragmentClickListener = context
    }

    override fun onDetach() {
        super.onDetach()
        mainFragmentClickListener = null
    }

    private fun hideProgressBar(){
        rvMovies.visibility = View.VISIBLE
        rvPopularNow.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        rvMovies.visibility = View.INVISIBLE
        rvPopularNow.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }
}