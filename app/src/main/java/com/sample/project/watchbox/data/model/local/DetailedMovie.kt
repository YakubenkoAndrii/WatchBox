package com.sample.project.watchbox.data.model.local

import android.os.Parcelable
import com.sample.project.watchbox.data.database.entities.MovieEntity
import com.sample.project.watchbox.data.model.remote.ResponseMovieDescription
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailedMovie(
    var title: String,
    var year: String,
    var rated: String,
    var released: String,
    var runtime: String,
    var genre: String,
    var actors: String,
    var posterImage: String,
    var imdbRating: String,
    var imdbVotes: String,
    var boxOffice: String
) : Parcelable {
    constructor(movie: ResponseMovieDescription) : this(
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
