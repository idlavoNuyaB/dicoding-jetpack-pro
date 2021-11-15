package com.freisia.vueee.ui.list.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freisia.vueee.data.remote.repository.MovieRepository
import com.freisia.vueee.model.movie.SearchMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    var listData = MutableLiveData<List<SearchMovie>>()
    var isLoading = MutableLiveData<Boolean>()
    var isFound = MutableLiveData<Boolean>()
    private var pageNP = 1
    private var page = 1
    private var pageTP = 1
    private var totalPages = 500

    fun reset(){
        page = 1
        pageNP = 1
        pageTP = 1
    }

    fun getData() = viewModelScope.launch(Dispatchers.IO){
        try{
            val response = repository.getList(page)
            withContext(Dispatchers.Main){
                isLoading.value = true
                if(response.isSuccessful){
                    if(!response.body()?.result.isNullOrEmpty()){
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
            }
        }  catch (e: Exception){
            withContext(Dispatchers.Main){
                isFound.value = false
                isLoading.value = false
            }
        }
    }

    fun getNowPlaying()= viewModelScope.launch(Dispatchers.IO) {
        try{
            val response = repository.getNowPlayingList(pageNP)
            withContext(Dispatchers.Main){
                isLoading.value = true
                if(response.isSuccessful){
                    if(!response.body()?.result.isNullOrEmpty()){
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
            }
        }  catch (e: Exception){
            withContext(Dispatchers.Main){
                isFound.value = false
                isLoading.value = false
            }
        }
    }

    fun getTopRated()= viewModelScope.launch(Dispatchers.IO) {
        try{
            val response = repository.getTopList(pageTP)
            withContext(Dispatchers.Main){
                isLoading.value = true
                if(response.isSuccessful){
                    if(!response.body()?.result.isNullOrEmpty()){
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
            }
        }  catch (e: Exception){
            withContext(Dispatchers.Main){
                isFound.value = false
                isLoading.value = false
            }
        }
    }

    fun onLoadMore(spinner: Int){
        if(spinner == 1){
            if(page <= totalPages){
                page++
                getData()
            }
        } else if(spinner == 2){
            if(pageNP <= totalPages){
                pageNP++
                getNowPlaying()
            }
        } else if(spinner == 3){
            if(pageTP <= totalPages){
                pageTP++
                getTopRated()
            }
        }
    }
}
