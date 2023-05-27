package com.laad.viniloapp.models

data class CommentRequest(
    val description: String,
    val rating: Int,
    val collector: Collector
)