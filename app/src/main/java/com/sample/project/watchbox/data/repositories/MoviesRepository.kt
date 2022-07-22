package com.sample.project.watchbox.data.repositories

import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.data.model.local.Movie
import io.reactivex.rxjava3.core.*

interface MoviesRepository {
    /**
     * returns list of movies by search query as Single<List>
     */
    fun searchMovies(query: String): Single<List<Movie>>

    /**
     * returns details about movie by movieID as DetailedMovie
     */
    fun getMovieDetails(movieId: String): Single<DetailedMovie>

    /**
     * returns saved list of movies from database as Single<List>
     */
    fun getMovies(): Single<List<DetailedMovie>>

    /**
     * returns is movie added to favorites by movieId from database as Observable<Boolean>
     */
    fun isAddedToFavorites(movieId: String): Observable<Boolean>

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


