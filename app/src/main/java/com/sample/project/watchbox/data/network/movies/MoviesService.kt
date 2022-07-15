package com.sample.project.watchbox.data.network.movies

import com.sample.project.watchbox.data.model.remote.ResponseMovieDescription
import com.sample.project.watchbox.data.model.remote.ResponseMovies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET(".")
    fun searchMovies(@Query("s") query: String): Single<ResponseMovies>

    @GET(".")
    fun getMovieDetails(@Query("i") movieId: String): Single<ResponseMovieDescription>
}
