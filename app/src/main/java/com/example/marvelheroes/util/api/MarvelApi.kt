package com.example.marvelheroes.util.api

import com.example.marvelheroes.util.model.Result
import com.example.marvelheroes.util.model.SuperHero
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import kotlin.random.Random

interface MarvelApi {

    //Fetches lists of comic characters with optional filters.
    @GET("v1/public/characters")
    fun listSuperHeroes(
        @Query("orderBy") orderBy: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("ts") ts: Long,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Call<SuperHero>
}