package com.sample.project.watchbox.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.project.watchbox.data.database.dao.MoviesDao
import com.sample.project.watchbox.data.database.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}
