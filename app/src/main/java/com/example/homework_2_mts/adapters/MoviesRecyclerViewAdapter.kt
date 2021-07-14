package com.example.homework_2_mts.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_2_mts.R
import com.example.homework_2_mts.data.dto.MovieDto
import com.squareup.picasso.Picasso

class MoviesRecyclerViewAdapter (private val list: List<MovieDto>)
    : RecyclerView.Adapter<MoviesRecyclerViewAdapter.MoviesViewHolder>(){

    var onMovieItemClick: ((MovieDto) -> Unit)? = null
    private var context: Context? = null
    private var movieItemRoot: ConstraintLayout? = null

    inner class MoviesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.movie_item, parent, false)) {
        private var moviePosterImg: ImageView? = null
        private var movieTitleText: TextView? = null
        private var movieDecorationText: TextView? = null
        private var ageRatingText: TextView? = null
        private var star1: ImageView? = null
        private var star2: ImageView? = null
        private var star3: ImageView? = null
        private var star4: ImageView? = null
        private var star5: ImageView? = null

        init {
            movieItemRoot = itemView.findViewById(R.id.movieItemRoot)
            moviePosterImg = itemView.findViewById(R.id.movie_poster_img)
            context = moviePosterImg?.context
            movieTitleText = itemView.findViewById(R.id.movieTitleText)
            movieDecorationText = itemView.findViewById(R.id.movieDescriptionText)
            ageRatingText = itemView.findViewById(R.id.movieAgeText)
            star1 = itemView.findViewById(R.id.star_1)
            star2 = itemView.findViewById(R.id.star_2)
            star3 = itemView.findViewById(R.id.star_3)
            star4 = itemView.findViewById(R.id.star_4)
            star5 = itemView.findViewById(R.id.star_5)
        }

        fun bind(movieDto: MovieDto, position: Int) {
//            if(position % 2 != 0){
//                val margin:Int = dpToPx(20)
//                val left:Int  = dpToPx(27)
//                val right:Int  = dpToPx(20)
//                val top:Int  = dpToPx(12)
//                val bottom:Int  = dpToPx(12)
//                val layoutParams = movieItemRoot?.layoutParams as GridLayoutManager.LayoutParams
//                layoutParams.setMargins(27, 0, 0, 0)
//                movieItemRoot?.layoutParams = layoutParams
//            }

            Picasso.get().load(movieDto.imageUrl).into(moviePosterImg);
            movieTitleText?.text = movieDto.title
            movieDecorationText?.text = movieDto.description
            ageRatingText?.text = movieDto.ageRestriction.toString() + '+'
            when(movieDto.rateScore){
                1 -> {
                    star1?.setImageResource(R.drawable.ic_star_active);
                    star2?.setImageResource(R.drawable.ic_star_no_active);
                    star3?.setImageResource(R.drawable.ic_star_no_active);
                    star4?.setImageResource(R.drawable.ic_star_no_active);
                    star5?.setImageResource(R.drawable.ic_star_no_active);
                }
                2 -> {
                    star1?.setImageResource(R.drawable.ic_star_active);
                    star2?.setImageResource(R.drawable.ic_star_active);
                    star3?.setImageResource(R.drawable.ic_star_no_active);
                    star4?.setImageResource(R.drawable.ic_star_no_active);
                    star5?.setImageResource(R.drawable.ic_star_no_active);
                }
                3 -> {
                    star1?.setImageResource(R.drawable.ic_star_active);
                    star2?.setImageResource(R.drawable.ic_star_active);
                    star3?.setImageResource(R.drawable.ic_star_active);
                    star4?.setImageResource(R.drawable.ic_star_no_active);
                    star5?.setImageResource(R.drawable.ic_star_no_active);
                }
                4 -> {
                    star1?.setImageResource(R.drawable.ic_star_active);
                    star2?.setImageResource(R.drawable.ic_star_active);
                    star3?.setImageResource(R.drawable.ic_star_active);
                    star4?.setImageResource(R.drawable.ic_star_active);
                    star5?.setImageResource(R.drawable.ic_star_no_active);
                }
                5 -> {
                    star1?.setImageResource(R.drawable.ic_star_active);
                    star2?.setImageResource(R.drawable.ic_star_active);
                    star3?.setImageResource(R.drawable.ic_star_active);
                    star4?.setImageResource(R.drawable.ic_star_active);
                    star5?.setImageResource(R.drawable.ic_star_active);
                }
            }

        }

    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return MoviesViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
            holder.bind(list[position], position)
            movieItemRoot?.setOnClickListener { onMovieItemClick?.invoke(list[position]) }
        }
        private fun dpToPx(dp: Int):Int{
            val px:Float = dp * context?.resources?.displayMetrics!!.density;
            return px.toInt()
        }
        override fun getItemCount(): Int = list.size
 }