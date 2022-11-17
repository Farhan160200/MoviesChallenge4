package com.farhanfarkaann.movieschallenge4.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.farhanfarkaann.movieschallenge4.hargapasar.HargaPasarData

@Database(entities = [HargaPasarData::class], version = 1)
abstract  class HargaPasarDatabase : RoomDatabase() {
    abstract fun moviesDao(): HargaPasarDao

    companion object{
        private var INSTANCE: HargaPasarDatabase? = null

        fun getInstance(context: Context): HargaPasarDatabase? {
            if(INSTANCE == null){
                synchronized(HargaPasarDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        HargaPasarDatabase::class.java,"HargaPasar.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}