package com.sample.project.watchbox.data.model.remote

import com.google.gson.annotations.SerializedName

data class ResponseMovies(@SerializedName("Search") val movieList: List<ResponseMovieItem>)

data class ResponseMovieItem(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("imdbID") val imdbId: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val posterImage: String
)
