package com.example.homework_2_mts.presentation.helpers

import com.example.homework_2_mts.repository.data.dto.MovieDto
import com.example.homework_2_mts.repository.data.dto.PopularNowDto

interface MainFragmentClickListener {
    fun onOpenDetailMovieClick(movieDto: MovieDto)
    fun onPopularNowClick(popularNowDto: PopularNowDto)
}