package com.farhanfarkaann.movieschallenge4.hargapasar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class HargaPasarData(
    @PrimaryKey(autoGenerate = true) var id : Int?,
    @ColumnInfo(name = "nama") var nama : String,
    @ColumnInfo(name = "harga") var harga : Int,
    @ColumnInfo(name = "deskripsi") var deskripsi : String,
//    @ColumnInfo(name = "tahun") var tahun : Int,
//    @ColumnInfo(name = "rating") var rating : Double,

    ) : Parcelable
