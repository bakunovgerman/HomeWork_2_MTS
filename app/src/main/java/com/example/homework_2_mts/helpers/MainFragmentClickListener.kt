package com.example.homework_2_mts.helpers

import com.example.homework_2_mts.data.dto.MovieDto
import com.example.homework_2_mts.data.dto.PopularNowDto

interface MainFragmentClickListener {
    fun onOpenDetailMovieClick(movieDto: MovieDto)
    fun onPopularNowClick(popularNowDto: PopularNowDto)
}