package com.farhanfarkaann.movieschallenge4.room

import androidx.room.*
import com.farhanfarkaann.movieschallenge4.movies.MoviesData

@Dao
interface MoviesDao {
    @Query("SELECT * FROM moviesdata")
    fun getAllStudent(): List<MoviesData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(moviesData: MoviesData):Long

    @Update
    fun updateStudent(moviesData: MoviesData):Int

    @Delete
    fun deleteStudent(moviesData: MoviesData):Int
}