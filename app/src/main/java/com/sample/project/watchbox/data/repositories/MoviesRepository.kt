package com.sample.project.watchbox.data.repositories

import com.sample.project.watchbox.data.database.dao.MoviesDao
import com.sample.project.watchbox.data.database.entities.MovieEntity
import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.data.model.local.Movie
import com.sample.project.watchbox.data.model.mapper.moviemapper.MoviesMapper
import com.sample.project.watchbox.data.network.movies.MoviesService
import io.reactivex.rxjava3.core.*
import javax.inject.Inject
import javax.inject.Singleton

interface MoviesRepository {
    fun searchMovies(query: String): Single<List<Movie>>
    fun getMovieDetails(movieId: String): Single<DetailedMovie>
    fun getMovies(): Single<List<DetailedMovie>>
    fun isAddedToFavorite(movieId: String): Observable<Boolean>
    fun addToFavorites(movieId: String, movieItem: DetailedMovie): Completable
    fun removeFromFavorites(movieId: String): Completable
    fun deleteAllFavorites(): Completable
}

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val moviesMapper: MoviesMapper,
    private val moviesDao: MoviesDao
) : MoviesRepository {

    override fun searchMovies(query: String): Single<List<Movie>> =
        moviesService.searchMovies(query).map { moviesMapper.mapToLocalMovieList(it.movieList) }

    override fun getMovieDetails(movieId: String): Single<DetailedMovie> =
        moviesService.getMovieDetails(movieId).map {
            moviesMapper.mapToLocalMovieDescription(it)
        }

    override fun getMovies(): Single<List<DetailedMovie>> =
        moviesDao.getAllMovies().map {
            moviesMapper.mapToLocalMoviesFromEntity(it)
        }

    override fun isAddedToFavorite(movieId: String): Observable<Boolean> =
        moviesDao.isAddedToFavorite(movieId).map { it > 0 }

    override fun addToFavorites(movieId: String, movieItem: DetailedMovie): Completable =
        moviesDao.addToFavorites(MovieEntity(movieId, movieItem))

    override fun removeFromFavorites(movieId: String): Completable =
        moviesDao.removeFromFavorites(movieId)

    override fun deleteAllFavorites(): Completable = moviesDao.deleteAll()

}
