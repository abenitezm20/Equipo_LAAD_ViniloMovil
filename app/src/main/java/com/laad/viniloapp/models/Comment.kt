package com.laad.viniloapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cached_comment")
data class Comment(
    @PrimaryKey val id: Int,
    val description: String,
    val rating: Int,
    val albumId: Int,
    val collectorId: Int,
): Serializable
