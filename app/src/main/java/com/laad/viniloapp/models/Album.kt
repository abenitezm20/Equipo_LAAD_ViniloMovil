package com.laad.viniloapp.models

import java.io.Serializable

data class Album(
    val id: Int,
    val name: String,
    val cover: String,
    val recordLabel: String,
    val releaseDate: String,
    val genre: String,
    val description: String,
) : Serializable
