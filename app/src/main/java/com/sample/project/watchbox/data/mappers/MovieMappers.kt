package com.sample.project.watchbox.data.mappers

import com.sample.project.watchbox.data.model.local.DetailedMovie
import com.sample.project.watchbox.data.model.network.NetworkMovieDescription

fun NetworkMovieDescription.toDetailedMovie(): DetailedMovie = DetailedMovie(this)