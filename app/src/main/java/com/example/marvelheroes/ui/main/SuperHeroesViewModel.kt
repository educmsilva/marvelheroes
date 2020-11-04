package com.example.marvelheroes.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelheroes.util.client.MarvelWebClient
import com.example.marvelheroes.util.model.Result

class SuperHeroesViewModel() : ViewModel() {

    private val superHeroesMutableLiveData =
        MutableLiveData<List<Result>?>().apply { value = null }
    private val errorMessageLiveData =
        MutableLiveData<String?>().apply { value = "" }

    private var offSetSearch: Int = 0

    fun getSuperHeroesLive(): LiveData<List<Result>?> = superHeroesMutableLiveData
    fun getErrorMessageLive():LiveData<String?> = errorMessageLiveData

    fun getSuperHeroes(
        limit: Int,
        offSet: Int
    ) {
        MarvelWebClient().listAllHeroes({
            updateRequest(it)
        }, {
            updateErrorMessage("Erro ao buscar os super herois. Erro: " + it)
        }, limit,
            offSet
        )
    }

    fun increaseOffSet() {
        offSetSearch = offSetSearch.plus(20)
    }

    fun decreaseOffSet() {
        if (offSetSearch > 0) {
            offSetSearch = offSetSearch.minus(20)
        }
    }

    fun getOffSet() = offSetSearch

    private fun updateRequest(superHeroes: List<Result>) {
        superHeroesMutableLiveData.postValue(superHeroes)
    }

    private fun updateErrorMessage(errorMessage:String){
        errorMessageLiveData.postValue(errorMessage)
    }

}