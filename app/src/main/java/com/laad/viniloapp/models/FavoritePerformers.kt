package com.laad.viniloapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cached_collector_performers")
data class FavoritePerformers(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val id_favorite_performers: Int,
    val name: String,
    val image: String,
    val description: String,
    var collectorId: Int
) : Serializable
