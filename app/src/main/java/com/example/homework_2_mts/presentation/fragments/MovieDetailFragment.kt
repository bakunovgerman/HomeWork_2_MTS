package com.example.homework_2_mts.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
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
import com.example.homework_2_mts.repository.database.entities.ActorEntity
import com.example.homework_2_mts.repository.database.entities.GenreEntity
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.example.homework_2_mts.repository.retrofit.entities.releaseDate.ReleaseDate
import com.squareup.picasso.Picasso

class MovieDetailFragment : Fragment() {

    private var movieEntity: MovieEntity? = null
    private lateinit var movieTitleTextView: TextView
    private lateinit var movieRatingLayout: RatingBar
    private lateinit var movieAgeTextView: TextView
    private lateinit var movieDescriptionTextView: TextView
    private lateinit var movieDateTextView: TextView
    private lateinit var moviePosterImageView: ImageView
    private lateinit var rvActors: RecyclerView
    private lateinit var rvGenres: RecyclerView
    private val actorAdapter: ActorsAdapter = ActorsAdapter()
    private val genresAdapter: PopularNowAdapter = PopularNowAdapter { }

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

        movieTitleTextView = view.findViewById(R.id.tvMovieTitle)
        movieRatingLayout = view.findViewById(R.id.rbMovie)
        movieAgeTextView = view.findViewById(R.id.tvMovieAge)
        movieDescriptionTextView = view.findViewById(R.id.tvMovieDescription)
        moviePosterImageView = view.findViewById(R.id.imgMoviePoster)
        movieDateTextView = view.findViewById(R.id.tvMovieDate)
        // set text
        movieEntity?.let {
            movieTitleTextView.text = it.title
            movieRatingLayout.rating = it.rateScore.toFloat()
            movieDescriptionTextView.text = it.description
            Picasso.get()
                .load(App.applicationContext.getString(R.string.bg_img_base_url) + it.bgUrl)
                .into(moviePosterImageView)
        }

        rvActors = view.findViewById(R.id.rvActors)
        rvGenres = view.findViewById(R.id.rvGenres)
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
            movieDetailFragmentViewModel.loadActors(it.id)
            movieDetailFragmentViewModel.loadReleaseDates(it.id)
            movieDetailFragmentViewModel.loadDetail(it.id)
        }

    }

    private fun initObserves() {
        movieDetailFragmentViewModel.getActors.observe(viewLifecycleOwner, Observer(::initActors))
        movieDetailFragmentViewModel.getReleaseDate.observe(
            viewLifecycleOwner,
            Observer(::setDateReleaseAndAgeRestriction)
        )
        movieDetailFragmentViewModel.getGenres.observe(viewLifecycleOwner, Observer(::initDetail))
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
            rvActors.addItemDecoration(SpacesItemDecoration(10, 20, list.size))
        }
    }

    private fun initDetail(list: List<GenreEntity>) {
        // сетим жанры
        genresAdapter.initData(list)
        if (rvGenres.itemDecorationCount == 0) {
            rvGenres.addItemDecoration(SpacesItemDecoration(10, 20, list.size))
        }
    }

    private fun setDateReleaseAndAgeRestriction(releaseDate: ReleaseDate) {
        Log.d("setAgeRestriction", "setAgeRestriction complete")
        movieAgeTextView.text = releaseDate.certification
        movieDateTextView.text = String.format("Дата релиза: ${releaseDate.releaseDate.substring(0,10).replace('-', '.')}")
    }

    companion object {
        const val MOVIE_KEY = "movie"
    }

}