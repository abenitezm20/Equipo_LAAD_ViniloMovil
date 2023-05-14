package com.laad.viniloapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Comments(
    @PrimaryKey(autoGenerate=true) val Id_coments: Int,
    val description: String,
    val rating: Int
): Serializable