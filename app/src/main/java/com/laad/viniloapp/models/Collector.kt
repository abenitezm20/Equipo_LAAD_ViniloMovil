package com.laad.viniloapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cached_collector")
data class Collector(
    @PrimaryKey val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
) : Serializable
