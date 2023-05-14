package com.laad.viniloapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.laad.viniloapp.utilities.Album_Status
import java.io.Serializable

@Entity
class CollectorAlbums (
    @PrimaryKey val id_collector_album :Int,
    val price: Double,
    val status: Album_Status
):Serializable