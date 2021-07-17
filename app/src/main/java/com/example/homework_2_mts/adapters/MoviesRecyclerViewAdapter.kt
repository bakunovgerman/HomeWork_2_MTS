package com.example.homework_2_mts.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.data.dto.MovieDto
import com.squareup.picasso.Picasso
import java.lang.IllegalStateException

class MoviesRecyclerViewAdapter (private val list: List<MovieDto>)
    : RecyclerView.Adapter<MoviesRecyclerViewAdapter.MoviesViewHolder>(){

    var onMovieItemClick: ((MovieDto) -> Unit)? = null
    private var context: Context? = null
    private var movieItemRoot: ConstraintLayout? = null

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ACTOR = 1
    }

    inner class MoviesViewHolder(inflater: View) :
        RecyclerView.ViewHolder(inflater) {

        private var moviePosterImg: ImageView? = null
        private var movieTitleText: TextView? = null
        private var movieDecorationText: TextView? = null
        private var ageRatingText: TextView? = null
        private var movieRatingLayout: RatingBar? = null

        init {
            movieItemRoot = itemView.findViewById(R.id.movieItemRoot)
            moviePosterImg = itemView.findViewById(R.id.movie_poster_img)
            context = moviePosterImg?.context
            movieTitleText = itemView.findViewById(R.id.movieTitleText)
            movieDecorationText = itemView.findViewById(R.id.movieDescriptionText)
            ageRatingText = itemView.findViewById(R.id.movieAgeText)
            movieRatingLayout = itemView.findViewById(R.id.movieRatingLayout)
            movieRatingLayout?.numStars = 5
        }

        fun bind(movieDto: MovieDto) {
            Picasso.get().load(movieDto.imageUrl).into(moviePosterImg);
            movieTitleText?.text = movieDto.title
            movieDecorationText?.text = movieDto.description
            ageRatingText?.text = movieDto.ageRestriction.toString() + '+'
            movieRatingLayout?.rating = movieDto.rateScore.toFloat()
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (position){
            0 -> VIEW_TYPE_HEADER
            else -> VIEW_TYPE_ACTOR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when(viewType) {
                VIEW_TYPE_HEADER ->  MoviesViewHolder(inflater.inflate(R.layout.movie_item, parent, false))
                VIEW_TYPE_ACTOR -> MoviesViewHolder(inflater.inflate(R.layout.movie_item, parent, false))
                else -> throw IllegalStateException()
            }
        }

        override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
            when(holder){
                is MoviesViewHolder -> {
                    holder.bind(list[position])
                    movieItemRoot?.setOnClickListener { onMovieItemClick?.invoke(list[position]) }
                }
            }
        }
        private fun dpToPx(dp: Int):Int{
            val px:Float = dp * context?.resources?.displayMetrics!!.density;
            return px.toInt()
        }
        override fun getItemCount(): Int = list.size
 }