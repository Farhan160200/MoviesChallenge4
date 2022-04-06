package com.farhanfarkaann.movieschallenge4.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.farhanfarkaann.movieschallenge4.movies.MoviesData

@Database(entities = [MoviesData::class], version = 1)
abstract  class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    companion object{
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase? {
            if(INSTANCE == null){
                synchronized(MoviesDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MoviesDatabase::class.java,"MoviesCoba.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}