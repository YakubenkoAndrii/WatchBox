package com.sample.project.watchbox.data.network.movies

import com.sample.project.watchbox.data.model.network.NetworkMovieDescription
import com.sample.project.watchbox.data.model.network.ResponseMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET(".")
    suspend fun searchMovies(@Query("s") query: String): ResponseMovies

    @GET(".")
    suspend fun getMovieDetails(@Query("i") movieId: String): NetworkMovieDescription
}
