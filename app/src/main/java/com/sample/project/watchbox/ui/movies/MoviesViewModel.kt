package com.sample.project.watchbox.ui.movies

import androidx.lifecycle.viewModelScope
import com.sample.project.watchbox.data.model.*
import com.sample.project.watchbox.data.model.error.HttpError
import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.data.model.local.Movie
import com.sample.project.watchbox.data.repositories.MoviesRepository
import com.sample.project.watchbox.ui.base.BaseViewModel
import com.sample.project.watchbox.utils.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : BaseViewModel() {

    private val _movies = MutableStateFlow(SearchMovieState(isLoading = false, error = null))
    val movies: StateFlow<SearchMovieState> = _movies.asStateFlow()
    fun searchMovies(query: String) {
        viewModelScope.launch {
            _movies.value = SearchMovieState(isLoading = true, error = null)
            when (val result = moviesRepository.searchMovies(query)) {
                is Result.Success -> {
                    _movies.value = SearchMovieState(moviesList = result.data, isLoading = false, error = null)
                }
                is Result.Error -> {
                    _movies.value = SearchMovieState(moviesList = null, isLoading = false, error = result.httpError?.message)
                }
            }
        }
    }

    private val _movie = MutableStateFlow(MovieDetailsState(isLoading = false, error = null))
    val movie: StateFlow<MovieDetailsState> = _movie.asStateFlow()
    fun getMovieDetails(movieId: String) {
        viewModelScope.launch {
            _movie.value = MovieDetailsState(isLoading = true, error = null)
            when (val result = moviesRepository.getMovieDetails(movieId)) {
                is Result.Success -> {
                    _movie.value = MovieDetailsState(detailedMovie = result.data, isLoading = false, error = null)
                }
                is Result.Error -> {
                    _movie.value = MovieDetailsState(detailedMovie = null, isLoading = false, error = result.httpError?.message)
                }
            }
        }
    }

    private val _localMovies = MutableStateFlow<DataWrapper<List<DetailedMovie>>>(EmptySuccess())
    val localMovies: StateFlow<DataWrapper<List<DetailedMovie>>> = _localMovies.asStateFlow()
    fun getMovies() {
        moviesRepository.getMovies()
            .doOnSubscribe { _localMovies.value = Loading(true) }
            .doOnTerminate { _localMovies.value = Loading(false) }
            .subBy(
                onError = { throwable ->
                    _localMovies.value = Failure(HttpError(throwable))
                },
                onSuccess = { moviesList ->
                    _localMovies.value = if (moviesList.isEmpty()) EmptySuccess() else Success(moviesList)
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

    fun isAddedToFavorite(movieId: String): Flow<Boolean> = moviesRepository.isAddedToFavorites(movieId)
}

data class SearchMovieState(
    val moviesList: List<Movie>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class MovieDetailsState(
    val detailedMovie: DetailedMovie? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
