package com.sample.project.watchbox.ui.movies

import com.sample.project.watchbox.data.model.*
import com.sample.project.watchbox.data.model.error.HttpError
import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.data.model.local.Movie
import com.sample.project.watchbox.data.repositories.MoviesRepository
import com.sample.project.watchbox.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : BaseViewModel() {

    private val _movies = MutableStateFlow<DataWrapper<List<Movie>>>(InitialSuccess())
    val movies: StateFlow<DataWrapper<List<Movie>>> = _movies
    fun searchMovies(query: String) {
        moviesRepository.searchMovies(query)
            .doOnSubscribe { _movies.value = Loading(true) }
            .doOnTerminate { _movies.value = Loading(false) }
            .subBy(
                onError = {
                    _movies.value = Failure(HttpError(it))
                },
                onSuccess = {
                    _movies.value = if (it.isEmpty()) EmptySuccess() else Success(it)
                }
            )
    }

    private val _movie = MutableStateFlow<DataWrapper<DetailedMovie>>(EmptySuccess())
    val movie: StateFlow<DataWrapper<DetailedMovie>> = _movie
    fun getMovieDetails(movieId: String) {
        moviesRepository.getMovieDetails(movieId)
            .doOnSubscribe { _movie.value = Loading(true) }
            .doOnTerminate { _movie.value = Loading(false) }
            .subBy(
                onError = {
                    _movie.value = Failure(HttpError(it))
                },
                onSuccess = {
                    _movie.value = Success(it)
                    isAddedToFavorite(movieId)
                }
            )
    }

    private val _localMovies = MutableStateFlow<DataWrapper<List<DetailedMovie>>>(EmptySuccess())
    val localMovies: StateFlow<DataWrapper<List<DetailedMovie>>> = _localMovies
    fun getMovies() {
        moviesRepository.getMovies()
            .doOnSubscribe { _localMovies.value = Loading(true) }
            .doOnTerminate { _localMovies.value = Loading(false) }
            .subBy(
                onError = {
                    _localMovies.value = Failure(HttpError(it))
                },
                onSuccess = {
                    _localMovies.value = if (it.isEmpty()) EmptySuccess() else Success(it)
                }
            )
    }

    fun addToFavorites(movieId: String, movieItem: DetailedMovie) {
        moviesRepository.addToFavorites(movieId, movieItem)
            .subBy(
                onComplete = {},
                onError = {}
            )
    }

    fun removeFromFavorites(movieId: String) {
        moviesRepository.removeFromFavorites(movieId)
            .subBy(
                onComplete = {},
                onError = {}
            )
    }

    fun deleteAllFavorites() {
        moviesRepository.deleteAllFavorites()
            .subBy(
                onComplete = { _localMovies.value = EmptySuccess() },
                onError = {}
            )
    }

    private val _isFavorite = MutableStateFlow<DataWrapper<Boolean>>(InitialSuccess())
    val isFavorite: StateFlow<DataWrapper<Boolean>> = _isFavorite
    private fun isAddedToFavorite(movieId: String) {
        moviesRepository.isAddedToFavorite(movieId)
            .subBy(
                onError = {
                    _isFavorite.value = Failure(HttpError(it))
                },
                onNext = { result ->
                    _isFavorite.value = Success(result)
                }
            )
    }
}
