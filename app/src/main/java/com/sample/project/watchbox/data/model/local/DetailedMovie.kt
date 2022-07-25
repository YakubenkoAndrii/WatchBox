package com.sample.project.watchbox.data.model.local

import android.os.Parcelable
import com.sample.project.watchbox.data.database.entities.MovieEntity
import com.sample.project.watchbox.data.model.network.NetworkMovieDescription
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailedMovie(
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val actors: String,
    val posterImage: String,
    val imdbRating: String,
    val imdbVotes: String,
    val boxOffice: String
) : Parcelable {
    constructor(movie: NetworkMovieDescription) : this(
        title = movie.title,
        year = movie.year,
        rated = movie.rated,
        released = movie.released,
        runtime = movie.runtime,
        genre = movie.genre,
        actors = movie.actors,
        posterImage = movie.posterImage,
        imdbRating = movie.imdbRating,
        imdbVotes = movie.imdbVotes,
        boxOffice = movie.boxOffice
    )
    constructor(movie: MovieEntity) : this(
        title = movie.title,
        year = movie.year,
        rated = movie.rated,
        released = movie.released,
        runtime = movie.runtime,
        genre = movie.genre,
        actors = movie.actors,
        posterImage = movie.posterImage,
        imdbRating = movie.imdbRating,
        imdbVotes = movie.imdbVotes,
        boxOffice = movie.boxOffice
    )
}
