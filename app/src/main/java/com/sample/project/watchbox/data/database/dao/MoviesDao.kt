package com.sample.project.watchbox.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.project.watchbox.data.database.entities.MovieEntity
import io.reactivex.rxjava3.core.*

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movie_table")
    fun getAllMovies(): Single<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavorites(item: MovieEntity): Completable

    @Query("DELETE FROM movie_table WHERE movieId= :movieId")
    fun removeFromFavorites(movieId: String): Completable

    @Query("SELECT count(*) FROM movie_table WHERE movieId= :movieId")
    fun isAddedToFavorite(movieId: String): Observable<Int>

    @Query("DELETE FROM movie_table")
    fun deleteAll(): Completable

}
