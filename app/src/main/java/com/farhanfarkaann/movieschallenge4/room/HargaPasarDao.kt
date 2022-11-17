package com.farhanfarkaann.movieschallenge4.room

import androidx.room.*
import com.farhanfarkaann.movieschallenge4.hargapasar.HargaPasarData

@Dao
interface HargaPasarDao {
    @Query("SELECT * FROM HargaPasarData")
    fun getAllStudent(): List<HargaPasarData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(hargaPasarData: HargaPasarData):Long

    @Update
    fun updateStudent(hargaPasarData: HargaPasarData):Int

    @Delete
    fun deleteStudent(hargaPasarData: HargaPasarData):Int
}