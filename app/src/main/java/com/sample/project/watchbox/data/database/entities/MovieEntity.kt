package com.sample.project.watchbox.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.utils.DatabaseConstants

@Entity(tableName = DatabaseConstants.MOVIE_TABLE)
data class MovieEntity(
    @PrimaryKey(autoGenerate = false) val movieId: String,
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
) {
    constructor(movieId: String, detailedMovie: DetailedMovie) : this(
        movieId = movieId,
        title = detailedMovie.title,
        year = detailedMovie.year,
        rated = detailedMovie.rated,
        released = detailedMovie.released,
        runtime = detailedMovie.runtime,
        genre = detailedMovie.genre,
        actors = detailedMovie.actors,
        posterImage = detailedMovie.posterImage,
        imdbRating = detailedMovie.imdbRating,
        imdbVotes = detailedMovie.imdbVotes,
        boxOffice = detailedMovie.boxOffice
    )
}
