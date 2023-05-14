package com.laad.viniloapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "cached_artist")
data class Artist(
    @PrimaryKey val id: Int,
    val name:String,
    val image:String,
    val description:String,
    val birthDate:String,
    val creationDate:String
) : Serializable
