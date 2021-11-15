package com.freisia.vueee.ui.daftar.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freisia.vueee.data.repository.MovieRepository
import com.freisia.vueee.model.movie.SearchMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    var listData = MutableLiveData<List<SearchMovie>>()
    var isLoading = MutableLiveData<Boolean>()
    var isFound = MutableLiveData<Boolean>()
    private var sizeItem = 20
    private var page = 1
    private var totalPages = 500

    fun getData() = viewModelScope.launch(Dispatchers.IO){
        try{
            val response = repository.getList(page)
            withContext(Dispatchers.Main){
                isLoading.value = true
                if(response.isSuccessful){
                    if(!response.body()?.result.isNullOrEmpty()){
                        if(sizeItem == response.body()?.result?.size){
                            listData.value = response.body()?.result
                            totalPages = response.body()?.totalPages as Int
                            isLoading.value = false
                            isFound.value = true
                        } else {
                            isLoading.value = false
                            isFound.value = false
                        }
                    } else {
                        isLoading.value = false
                        isFound.value = false
                    }
                } else {
                    isLoading.value = false
                    isFound.value = false
                }

            }
        }  catch (e: Exception){
            withContext(Dispatchers.Main){
                isFound.value = false
                isLoading.value = false
            }
        }
    }

    fun onLoadMore(){
        if(page <= totalPages){
            page++
            getData()
        }
    }
}
