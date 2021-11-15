package com.freisia.vueee.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freisia.vueee.data.repository.MovieRepository
import com.freisia.vueee.data.repository.TVRepository
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.tv.TV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel <T,Z>(private val repository: T) : ViewModel(){
    var listData = MutableLiveData<Z>()
    var isLoading = MutableLiveData<Boolean>()
    var isFound = MutableLiveData<Boolean>()
    var rate = MutableLiveData<String>()

    fun getData(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try{
            withContext(Dispatchers.Main) {
                isLoading.value = true
            }
            if (repository is MovieRepository) {
                val response = repository.getDetail(id)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        if(response.body() is Movie){
                            listData.value = response.body() as Z
                            isLoading.value = false
                            isFound.value = true
                        } else {
                            isLoading.value = false
                            isFound.value = false
                        }
                    }
                } else {
                    isLoading.value = false
                    isFound.value = false
                }
            } else if(repository is TVRepository){
                val response = repository.getDetail(id)
                val rating = repository.getRating(id)
                if (response.isSuccessful && rating.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        if(response.body() is TV){
                            listData.value = response.body() as Z
                            isLoading.value = false
                            isFound.value = true
                            for(i in 0 until rating.body()?.results?.size!!){
                                if(rating.body()!!.results[i].iso_3166_1 == "US"){
                                    rate.value = rating.body()?.results!![i].rating
                                    break
                                }
                            }
                        } else {
                            isLoading.value = false
                            isFound.value = false
                        }
                    }
                } else {
                    isLoading.value = false
                    isFound.value = false
                }
            }
        } catch (e: Exception){
            withContext(Dispatchers.Main){
                isFound.value = false
                isLoading.value = false
            }
        }
    }
}