package com.sample.project.watchbox.data.repositories

import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.data.model.local.Movie
import com.sample.project.watchbox.utils.result.Result
import io.reactivex.rxjava3.core.*
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    /**
     * returns list of movies by search query as Single<List>
     */
    suspend fun searchMovies(query: String): Result<List<Movie>>

    /**
     * returns details about movie by movieID as DetailedMovie
     */
    suspend fun getMovieDetails(movieId: String): Result<DetailedMovie>

    /**
     * returns saved list of movies from database as Single<List>
     */
    fun getMovies(): Single<List<DetailedMovie>>

    /**
     * returns is movie added to favorites by movieId from database as Flow<Boolean>
     */
    fun isAddedToFavorites(movieId: String): Flow<Boolean>

    /**
     * adding movie to favorites as Completable
     */
    fun addToFavorites(movieId: String, movieItem: DetailedMovie): Completable

    /**
     * removing movie from favorites as Completable
     */
    fun removeFromFavorites(movieId: String): Completable

    /**
     * removing all movies from database as Completable
     */
    fun deleteAllFavorites(): Completable
}
