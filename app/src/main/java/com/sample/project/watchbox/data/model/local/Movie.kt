package com.sample.project.watchbox.data.model.local

import android.os.Parcelable
import com.sample.project.watchbox.data.model.remote.ResponseMovieItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val year: String,
    val imdbId: String,
    val type: String,
    val posterImage: String,
) : Parcelable {
    constructor(movie: ResponseMovieItem) : this(
        title = movie.title,
        year = movie.year,
        imdbId = movie.imdbId,
        type = movie.type,
        posterImage = movie.posterImage
    )
}
