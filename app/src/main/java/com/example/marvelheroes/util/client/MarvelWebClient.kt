package com.example.marvelheroes.util.client

import com.example.marvelheroes.util.RetrofitInitializer
import com.example.marvelheroes.util.callback
import com.example.marvelheroes.util.model.Result
import com.example.marvelheroes.util.model.SuperHero
import java.security.MessageDigest
import java.util.*
import kotlin.random.Random

class MarvelWebClient {

    private fun byteArrayToHexString(array: Array<Byte>): String {
        val result = StringBuilder(array.size * 2)
        for (byte in array) {
            val toAppend =
                String.format("%2x", byte).replace(" ", "0") // hexadecimal
            result.append(toAppend)
        }
        return result.toString()
    }

    private fun toMD5Hash(text: String): String {
        var result = ""
        try {
            val md5 = MessageDigest.getInstance("MD5")
            val md5HashBytes = md5.digest(text.toByteArray()).toTypedArray()
            result = byteArrayToHexString(md5HashBytes)
        } catch (e: Exception) {
            result = "error: ${e.message}"
        }
        return result
    }

    fun listAllHeroes(
        success: (superHeroes: List<Result>) -> Unit,
        failure: (throwable: Throwable) -> Unit,
        limit:Int,
        offSet:Int
    ) {
        val apikey = "8b4a417f1a1ca2208adddd0fef2cd393"
        val ts: Long = Random.nextLong(0, Date().time)
        val hash: String = toMD5Hash(ts.toString() + "dea15529daadd0b0994d4a20a9215fde95240e93" + apikey)

        val call = RetrofitInitializer().marvelApiService().listSuperHeroes(
            "name",
            limit,
            offSet,
            ts,
            apikey,
            hash
        )

        call.enqueue(
            callback({ response ->
                response?.body()?.let {
                    success(it.data.results)
                }
            },
                { throwable ->
                    throwable?.let {
                        failure(it)
                    }
                })
        )
    }

}