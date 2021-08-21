package com.example.homework_2_mts.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
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
import com.example.homework_2_mts.presentation.adapters.items_decoration.SpacesItemDecoration
import com.example.homework_2_mts.repository.database.entities.ActorEntity
import com.example.homework_2_mts.repository.database.entities.MovieEntity
import com.squareup.picasso.Picasso

class MovieDetailFragment : Fragment() {

    private var movieEntity: MovieEntity? = null
    private lateinit var tvMovieTitle: TextView
    private lateinit var movieRatingLayout: RatingBar
    private lateinit var tvMovieAge: TextView
    private lateinit var tvMovieDescription: TextView
    private lateinit var imgMoviePoster: ImageView
    private lateinit var rvActors: RecyclerView
    private lateinit var actorAdapter: ActorsAdapter

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

        tvMovieTitle = view.findViewById(R.id.tvMovieTitle)
        movieRatingLayout = view.findViewById(R.id.rbMovie)
        tvMovieAge = view.findViewById(R.id.tvMovieAge)
        tvMovieDescription = view.findViewById(R.id.tvMovieDescription)
        imgMoviePoster = view.findViewById(R.id.imgMoviePoster)
        // set text
        movieEntity?.let {
            tvMovieTitle.text = it.title
            movieRatingLayout.rating = it.rateScore.toFloat()
            tvMovieAge.text = String.format(it.ageRestriction.toString() + '+')
            tvMovieDescription.text = it.description
            Picasso.get()
                .load(App.applicationContext.getString(R.string.bg_img_base_url) + it.bgUrl)
                .into(imgMoviePoster)
        }

        rvActors = view.findViewById(R.id.rvActors)
        actorAdapter  = ActorsAdapter()
        rvActors.apply {
            adapter = actorAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        movieDetailFragmentViewModel.getActors.observe(viewLifecycleOwner, Observer(::initActors))
        movieEntity?.let {
            movieDetailFragmentViewModel.loadActors(it.id)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    private fun initActors(list: List<ActorEntity>) {
        actorAdapter.initData(list)
        if (rvActors.itemDecorationCount == 0){
            rvActors.addItemDecoration(SpacesItemDecoration(10, 20, list.size))
        }
    }

    companion object {
        const val MOVIE_KEY = "movie"
    }

}