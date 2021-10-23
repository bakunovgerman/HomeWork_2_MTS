package com.example.homework_2_mts.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.App
import com.example.homework_2_mts.R
import com.example.homework_2_mts.domain.MovieDetailFragmentViewModel
import com.example.homework_2_mts.presentation.adapters.ActorsAdapter
import com.example.homework_2_mts.presentation.adapters.PopularNowAdapter
import com.example.homework_2_mts.presentation.adapters.items_decoration.SpacesItemDecoration
import com.example.homework_2_mts.presentation.helpers.MainFragmentClickListener
import com.example.homework_2_mts.presentation.helpers.ViewStateLayout
import com.example.homework_2_mts.repository.database.entities.ActorEntity
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.retrofit.entities.releaseDate.ReleaseDate
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class MovieDetailFragment : Fragment() {

    private var movieEntity: MovieEntity? = null
    private lateinit var movieTitleTextView: TextView
    private lateinit var movieRatingLayout: RatingBar
    private lateinit var movieAgeTextView: TextView
    private lateinit var movieDescriptionTextView: TextView
    private lateinit var movieDateTextView: TextView
    private lateinit var moviePosterImageView: ImageView
    private lateinit var progressBarFrameLayout: FrameLayout
    private lateinit var bottomSheetBehaviorCardView: CardView
    private lateinit var fragmentDetailMovieRootView: CoordinatorLayout
    private lateinit var rvActors: RecyclerView
    private lateinit var rvGenres: RecyclerView
    private val actorAdapter: ActorsAdapter = ActorsAdapter {
        mainFragmentClickListener?.onOpenDetailActorClick(it)
    }
    private val genresAdapter: PopularNowAdapter = PopularNowAdapter { }
    private var mainFragmentClickListener: MainFragmentClickListener? = null

    // ViewModels
    private lateinit var movieDetailFragmentViewModel: MovieDetailFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieEntity = requireArguments().getParcelable<MovieEntity>(MOVIE_KEY)
        movieDetailFragmentViewModel =
            ViewModelProvider(this).get(MovieDetailFragmentViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init view
        movieTitleTextView = view.findViewById(R.id.tvMovieTitle)
        movieRatingLayout = view.findViewById(R.id.rbMovie)
        movieAgeTextView = view.findViewById(R.id.tvMovieAge)
        movieDescriptionTextView = view.findViewById(R.id.tvMovieDescription)
        moviePosterImageView = view.findViewById(R.id.imgMoviePoster)
        movieDateTextView = view.findViewById(R.id.tvMovieDate)
        rvActors = view.findViewById(R.id.rvActors)
        rvGenres = view.findViewById(R.id.rvGenres)
        progressBarFrameLayout = view.findViewById(R.id.progressBar)
        bottomSheetBehaviorCardView = view.findViewById(R.id.cvBottomSheetBehavior)
        fragmentDetailMovieRootView = view.findViewById(R.id.fragmentDetailMovieRootView)
        // set visible
        movieDateTextView.visibility = View.GONE
        movieAgeTextView.visibility = View.GONE
        rvGenres.visibility = View.GONE
        bottomSheetBehaviorCardView.visibility = View.GONE
        // set text
        movieEntity?.let {
            movieTitleTextView.text = it.title
            movieRatingLayout.rating = it.rateScore.toFloat()
            movieDescriptionTextView.text = it.description
            val bgUrl = if (it.bgUrl != null) it.bgUrl else it.posterUrl
            Picasso.get()
                .load(App.applicationContext.getString(R.string.bg_img_base_url) + bgUrl)
                .into(moviePosterImageView)
        }
        rvActors.apply {
            adapter = actorAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        rvGenres.apply {
            adapter = genresAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        // иниц. подписки
        initObserves()
        // загружаем актеров
        movieEntity?.let {
            movieDetailFragmentViewModel.loadData(it.id)
        }

    }

    private fun initObserves() {
        movieDetailFragmentViewModel.getActors.observe(viewLifecycleOwner, Observer(::initActors))
        movieDetailFragmentViewModel.getReleaseDate.observe(
            viewLifecycleOwner,
            Observer(::setDateReleaseAndAgeRestriction)
        )
        movieDetailFragmentViewModel.getGenres.observe(viewLifecycleOwner, Observer(::initDetail))
        movieDetailFragmentViewModel.getViewState.observe(
            viewLifecycleOwner,
            Observer(::setViewState)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    private fun initActors(list: List<ActorEntity>) {
        actorAdapter.initData(list)
        if (rvActors.itemDecorationCount == 0) {
            rvActors.addItemDecoration(
                SpacesItemDecoration(
                    App.applicationContext.resources.getDimension(R.dimen.actor_margin_right),
                    App.applicationContext.resources.getDimension(R.dimen.actor_margin_left),
                    list.size
                )
            )
        }
    }

    // проверяем viewState
    private fun setViewState(viewStateLayout: ViewStateLayout) = with(viewStateLayout) {
        if (this.isDownloaded) {
            progressBarFrameLayout.visibility = View.GONE
            bottomSheetBehaviorCardView.visibility = View.VISIBLE
        } else if (this.e != null) {
            Snackbar.make(
                fragmentDetailMovieRootView,
                String.format(
                    App.applicationContext.getString(R.string.error_snack_bar),
                    this.e.toString()
                ),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun initDetail(list: List<GenreEntity>) {
        // сетим жанры
        if (list.isNotEmpty()) {
            genresAdapter.initData(list)
            if (rvGenres.itemDecorationCount == 0) {
                rvGenres.addItemDecoration(
                    SpacesItemDecoration(
                        App.applicationContext.resources.getDimension(R.dimen.actor_margin_right),
                        App.applicationContext.resources.getDimension(R.dimen.actor_margin_left),
                        list.size
                    )
                )
            }
            rvGenres.visibility = View.VISIBLE
        }
    }

    private fun setDateReleaseAndAgeRestriction(releaseDate: ReleaseDate) {
        val age = releaseDate.certification
        val date = String.format(
            App.applicationContext.getString(R.string.movie_date_release),
            releaseDate.releaseDate.substring(0, 10).replace('-', '.')
        )
        if (age != "") {
            movieAgeTextView.text = age
            movieAgeTextView.visibility = View.VISIBLE
        }
        if (date != "") {
            movieDateTextView.text = date
            movieDateTextView.visibility = View.VISIBLE
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

    companion object {
        const val MOVIE_KEY = "movie"
    }

}