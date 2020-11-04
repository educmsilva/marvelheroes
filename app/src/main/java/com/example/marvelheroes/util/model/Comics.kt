package com.example.marvelheroes.util.model

data class Comics (
    val available: String,
    val returned: String,
    val collectionURI: String,
    val items: List<ComicsItem>
)