package com.laad.viniloapp.models

import androidx.room.PrimaryKey
import java.io.Serializable

data class Collector(
    @PrimaryKey val id: Int,
    val name: String,
    val telephone: String,
    val email: String
): Serializable