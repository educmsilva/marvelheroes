package com.example.marvelheroes.util.model

data class Result (
    val id: String,
    val name: String,
    val description: String,
    val modified: String,
    val resourceURI: String,
    val urls: List<URL>,
    val thumbnail: Thumbnail,
    val comics: Comics,
    val stories: Stories,
    val events: Comics,
    val series: Comics
)
