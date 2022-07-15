package com.sample.project.watchbox.data.model.mapper.moviemapper

import com.sample.project.watchbox.data.database.entities.MovieEntity
import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.data.model.local.Movie
import com.sample.project.watchbox.data.model.remote.ResponseMovieDescription
import com.sample.project.watchbox.data.model.remote.ResponseMovieItem

open class MoviesMapper {
    fun mapToLocalMovieList(response: List<ResponseMovieItem>): List<Movie> = response.map { Movie(it) }
    fun mapToLocalMovieDescription(response: ResponseMovieDescription): DetailedMovie = DetailedMovie(response)
    fun mapToLocalMoviesFromEntity(movies: List<MovieEntity>): List<DetailedMovie> = movies.map { DetailedMovie(it) }
}
