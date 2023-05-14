package com.laad.viniloapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class FavoritePerformers (
    @PrimaryKey val id_favorite_performers:Int,
    val name:String
    /*,val image: String,
    val description: String,
    val creationDate:String*/
):Serializable