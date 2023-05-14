package com.laad.viniloapp.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable
@Entity
data class Collector(
    @PrimaryKey val id_collector: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<Comments>,
    val favoritePerformers: List<FavoritePerformers>,
    val collectorAlbums: List<CollectorAlbums>
): Serializable

data class CommentCollector(
    @Embedded val collector:Collector,
    @Relation(
        parentColumn  = "id_collector",
        entityColumn  = "id_collector_comments"
            )
    val comments: List<Comments>
)

data class CollectorAlbum(
    @Embedded val collector: Collector,
    @Relation(
        parentColumn ="id_collector",
        entityColumn="id_collector_albums"
    )
    val collectorAlbums: List<CollectorAlbums>
)
@Entity(primaryKeys=["id_collector","id_favorite_performers"])
data class PerformersCollector(
    val id_collector:Int,
    val id_favorite_performers:Int
)

