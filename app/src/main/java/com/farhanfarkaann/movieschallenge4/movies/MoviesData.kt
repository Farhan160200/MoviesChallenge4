package com.farhanfarkaann.movieschallenge4.movies

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class MoviesData(
    @PrimaryKey(autoGenerate = true) var id : Int?,
    @ColumnInfo(name = "judul") var judul : String,
    @ColumnInfo(name = "durasi") var durasi : Int,
    @ColumnInfo(name = "sinopsis") var sinopsis : String,
    @ColumnInfo(name = "tahun") var tahun : Int,
    @ColumnInfo(name = "rating") var rating : Double,

    ) : Parcelable
