package com.example.marvelheroes.util

import com.example.marvelheroes.util.api.MarvelApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    companion object {
        fun getRetrofitInstance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    fun marvelApiService() = getRetrofitInstance().create(MarvelApi::class.java)
}