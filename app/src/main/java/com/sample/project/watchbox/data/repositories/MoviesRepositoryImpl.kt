package com.sample.project.watchbox.data.repositories

import com.sample.project.watchbox.data.database.dao.MoviesDao
import com.sample.project.watchbox.data.database.entities.MovieEntity
import com.sample.project.watchbox.data.mappers.toDetailedMovie
import com.sample.project.watchbox.data.model.error.HttpError
import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.data.model.local.Movie
import com.sample.project.watchbox.data.network.movies.MoviesService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import com.sample.project.watchbox.utils.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    private val moviesDao: MoviesDao
) : MoviesRepository {

    override suspend fun searchMovies(query: String): Result<List<Movie>> = try {
        Result.Success(
            data = moviesService.searchMovies(query).movieList
                .map { item -> Movie(item) }
        )
    } catch (e: Exception) {
        Result.Error(HttpError(e), null)
    }

    override suspend fun getMovieDetails(movieId: String): Result<DetailedMovie> = try {
        Result.Success(
            data = moviesService.getMovieDetails(movieId).toDetailedMovie()
        )
    } catch (e: Exception) {
        Result.Error(HttpError(e), null)
    }

    override fun getMovies(): Single<List<DetailedMovie>> =
        moviesDao.getAllMovies().map { result -> result.map { item -> DetailedMovie(item) } }

    override fun isAddedToFavorites(movieId: String): Flow<Boolean> =
        moviesDao.isAddedToFavorite(movieId).map { it > 0 }

    override fun addToFavorites(movieId: String, movieItem: DetailedMovie): Completable =
        moviesDao.addToFavorites(MovieEntity(movieId, movieItem))

    override fun removeFromFavorites(movieId: String): Completable =
        moviesDao.removeFromFavorites(movieId)

    override fun deleteAllFavorites(): Completable = moviesDao.deleteAll()
}
