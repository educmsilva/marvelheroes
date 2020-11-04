package com.example.marvelheroes.util.model

data class Stories (
    val available: String,
    val returned: String,
    val collectionURI: String,
    val items: List<StoriesItem>
)