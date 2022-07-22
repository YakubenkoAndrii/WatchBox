package com.sample.project.watchbox.data.repositories

import com.sample.project.watchbox.data.database.dao.MoviesDao
import com.sample.project.watchbox.data.database.entities.MovieEntity
import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.data.model.local.Movie
import com.sample.project.watchbox.data.network.movies.MoviesService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val moviesDao: MoviesDao
) : MoviesRepository {

    override fun searchMovies(query: String): Single<List<Movie>> =
        moviesService.searchMovies(query).map { result -> result.movieList.map { item -> Movie(item) } }

    override fun getMovieDetails(movieId: String): Single<DetailedMovie> =
        moviesService.getMovieDetails(movieId).map { result -> DetailedMovie(result) }

    override fun getMovies(): Single<List<DetailedMovie>> =
        moviesDao.getAllMovies().map { result -> result.map { item -> DetailedMovie(item) } }

    override fun isAddedToFavorites(movieId: String): Observable<Boolean> =
        moviesDao.isAddedToFavorite(movieId).map { it > 0 }

    override fun addToFavorites(movieId: String, movieItem: DetailedMovie): Completable =
        moviesDao.addToFavorites(MovieEntity(movieId, movieItem))

    override fun removeFromFavorites(movieId: String): Completable =
        moviesDao.removeFromFavorites(movieId)

    override fun deleteAllFavorites(): Completable = moviesDao.deleteAll()
}
