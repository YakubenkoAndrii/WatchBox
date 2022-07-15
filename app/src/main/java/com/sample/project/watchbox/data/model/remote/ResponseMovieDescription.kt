package com.sample.project.watchbox.data.model.remote

import com.google.gson.annotations.SerializedName

data class ResponseMovieDescription(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Rated") val rated: String,
    @SerializedName("Released") val released: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Poster") val posterImage: String,
    @SerializedName("imdbRating") val imdbRating: String,
    @SerializedName("imdbVotes") val imdbVotes: String,
    @SerializedName("BoxOffice") val boxOffice: String
)
