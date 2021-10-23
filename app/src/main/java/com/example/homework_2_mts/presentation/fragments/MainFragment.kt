package com.example.homework_2_mts.presentation.fragments

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.databinding.FragmentMainBinding
import com.example.homework_2_mts.presentation.adapters.MoviesAdapter
import com.example.homework_2_mts.presentation.adapters.PopularNowAdapter
import com.example.homework_2_mts.presentation.adapters.items_decoration.GridSpacingItemDecoration
import com.example.homework_2_mts.presentation.adapters.items_decoration.SpacesItemDecoration
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.presentation.helpers.MainFragmentClickListener
import com.example.homework_2_mts.presentation.helpers.MoviesCallbackDiffUtils
import com.example.homework_2_mts.domain.MainFragmentViewModel
import com.example.homework_2_mts.presentation.adapters.items_decoration.FooterItemDecoration
import com.example.homework_2_mts.presentation.helpers.ViewStateLayout
import com.google.android.material.textfield.TextInputLayout

class MainFragment : Fragment() {

    // init viewModel
    private lateinit var mainFragmentViewModel: MainFragmentViewModel

    // init view
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var rvMovies: RecyclerView
    private lateinit var rvPopularNow: RecyclerView
    private lateinit var progressBar: FrameLayout
    private lateinit var imgBtnSearch: ImageView
    private lateinit var searchTextInputLayout: TextInputLayout
    private lateinit var tvPopularNowTitle: TextView
    private lateinit var searchEditText: EditText
    private var mainFragmentClickListener: MainFragmentClickListener? = null
    private lateinit var binding: FragmentMainBinding
    private var isShowedSearch = false

    //init adapter
    private lateinit var popularNowAdapter: PopularNowAdapter
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainFragmentViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        binding = FragmentMainBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        rvMovies = view.findViewById(R.id.rvMovies)
        rvPopularNow = view.findViewById(R.id.rvPopularNow)
        progressBar = view.findViewById(R.id.progressBar)
        imgBtnSearch = view.findViewById(R.id.imgBtnSearch)
        searchTextInputLayout = view.findViewById(R.id.searchTextInputLayout)
        searchEditText = view.findViewById(R.id.searchEditText)

        tvPopularNowTitle = view.findViewById(R.id.tvPopularNowTitle)

        imgBtnSearch.setOnClickListener { imgBtnSearch ->
            if (isShowedSearch) {
                isShowedSearch = false
                searchTextInputLayout.visibility = View.INVISIBLE
                rvPopularNow.visibility = View.VISIBLE
                tvPopularNowTitle.visibility = View.VISIBLE
                context?.let { context ->
                    imgBtnSearch.background = AppCompatResources.getDrawable(context, R.drawable.ic_search)
                }
                searchEditText.text.clear()
                mainFragmentViewModel.loadData()
            } else {
                isShowedSearch = true
                searchTextInputLayout.visibility = View.VISIBLE
                rvPopularNow.visibility = View.INVISIBLE
                tvPopularNowTitle.visibility = View.GONE
                moviesAdapter.initData(emptyList())
                context?.let { context ->
                    imgBtnSearch.background = AppCompatResources.getDrawable(context, R.drawable.ic_close)
                }
            }
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                mainFragmentViewModel.getSearchMovies(s.toString())
            }

        })


        // init RecyclerView
        popularNowAdapter =
            PopularNowAdapter {
                mainFragmentClickListener?.onPopularNowClick(it)
            }
        moviesAdapter =
            MoviesAdapter {
                mainFragmentClickListener?.onOpenDetailMovieClick(it)
            }

        rvPopularNow.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularNowAdapter
        }

        rvMovies.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = moviesAdapter
            addItemDecoration(GridSpacingItemDecoration(50))
        }

        // init listener
        swipeRefresh.setOnRefreshListener {
            showProgressBar()
            mainFragmentViewModel.loadData()
        }

        // init observe
        mainFragmentViewModel.moviesList.observe(
            viewLifecycleOwner,
            Observer(moviesAdapter::initData)
        )
        mainFragmentViewModel.genresList.observe(
            viewLifecycleOwner,
            Observer(::initPopularNowData)
        )
        mainFragmentViewModel.viewStateLayout.observe(viewLifecycleOwner, Observer(::setViewState))
        mainFragmentViewModel.updateMoviesList.observe(viewLifecycleOwner, Observer(::updateData))

        mainFragmentViewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    private fun initPopularNowData(popularNowItems: List<GenreEntity>?) {
        if (popularNowItems != null) {
            Log.d("addItemDecoration", "addItemDecoration rvPopular")
            popularNowAdapter.initData(popularNowItems)
            if (rvPopularNow.itemDecorationCount == 0) {
                rvPopularNow.addItemDecoration(
                    SpacesItemDecoration(
                        App.applicationContext.resources.getDimension(R.dimen.genre_margin_right),
                        App.applicationContext.resources.getDimension(R.dimen.genre_margin_left),
                        popularNowItems.size
                    )
                )
            }

        }
    }

    private fun setViewState(viewStateLayout: ViewStateLayout) = with(viewStateLayout) {
        if (isDownloaded) {
            hideProgressBar()
        } else {
            showProgressBar()
        }
    }

    private fun updateData(newList: List<MovieEntity>) {
        val oldList = moviesAdapter.items
        val callback = MoviesCallbackDiffUtils(oldList, newList)
        val diff = DiffUtil.calculateDiff(callback)
        diff.dispatchUpdatesTo(moviesAdapter)
        moviesAdapter.initData(newList)
        swipeRefresh.isRefreshing = false

        hideProgressBar()
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

    private fun hideProgressBar() {
        if (swipeRefresh.isRefreshing)
            swipeRefresh.isRefreshing = false
        rvMovies.visibility = View.VISIBLE
        rvPopularNow.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        rvMovies.visibility = View.INVISIBLE
        rvPopularNow.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }
}