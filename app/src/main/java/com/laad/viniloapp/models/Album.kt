package com.laad.viniloapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cached_albums")
data class Album(
    @PrimaryKey val id: Int,
    val name: String,
    val cover: String,
    val recordLabel: String,
    var releaseDate: String,
    val genre: String,
    val description: String,
) : Serializable
