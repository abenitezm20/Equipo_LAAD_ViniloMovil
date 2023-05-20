package com.laad.viniloapp.models

data class AlbumRequest(
    val name: String,
    val cover: String,
    val recordLabel: String,
    val releaseDate: String,
    val genre: String,
    val description: String,
)