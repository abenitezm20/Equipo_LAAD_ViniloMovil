package com.laad.viniloapp.models

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class CollectorPerformers(
    @Embedded val collector: Collector,
    @Relation(
        parentColumn = "id_collector",
        entityColumn = "collectorId"
    )
    val favoritePerformers: List<FavoritePerformers>
) : Serializable
