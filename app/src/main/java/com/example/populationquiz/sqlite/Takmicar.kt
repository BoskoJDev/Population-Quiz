package com.example.populationquiz.sqlite

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "takmicari")
data class Takmicar(
    @PrimaryKey val nalog: String,
    val sifra: String,
    var highscore: Int,
    var poeni: Int = 0
) : Parcelable