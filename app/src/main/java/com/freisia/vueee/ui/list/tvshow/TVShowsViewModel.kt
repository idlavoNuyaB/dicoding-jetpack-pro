package com.freisia.vueee.ui.list.tvshow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freisia.vueee.data.remote.repository.TVRepository
import com.freisia.vueee.model.tv.SearchTV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TVShowsViewModel(private val repository: TVRepository) : ViewModel() {
    var listData = MutableLiveData<List<SearchTV>>()
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

    fun getData() = viewModelScope.launch(Dispatchers.IO) {
        try {
            withContext(Dispatchers.Main) {
                isLoading.value = true
            }
            val response = repository.getList(page)
            if (response.isSuccessful) {
                if (!response.body()?.result.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        listData.value = response.body()?.result
                        isLoading.value = false
                        isFound.value = true
                        totalPages = response.body()?.totalPages as Int
                    }
                } else {
                    isLoading.value = false
                    isFound.value = false
                }
            } else {
                isLoading.value = false
                isFound.value = false
            }
        } catch(e:Exception) {
            withContext(Dispatchers.Main) {
                isFound.value = false
                isLoading.value = false
            }
        }
    }

    fun getOnAirData() = viewModelScope.launch(Dispatchers.IO) {
        try {
            withContext(Dispatchers.Main) {
                isLoading.value = true
            }
            val response = repository.getOnAirList(pageNP)
            if (response.isSuccessful) {
                if (!response.body()?.result.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        listData.value = response.body()?.result
                        isLoading.value = false
                        isFound.value = true
                        totalPages = response.body()?.totalPages as Int
                    }
                } else {
                    isLoading.value = false
                    isFound.value = false
                }
            } else {
                isLoading.value = false
                isFound.value = false
            }
        } catch(e:Exception) {
            withContext(Dispatchers.Main) {
                isFound.value = false
                isLoading.value = false
            }
        }
    }

    fun getTopRated() = viewModelScope.launch(Dispatchers.IO) {
        try {
            withContext(Dispatchers.Main) {
                isLoading.value = true
            }
            val response = repository.getTopRatedList(pageTP)
            if (response.isSuccessful) {
                if (!response.body()?.result.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        listData.value = response.body()?.result
                        isLoading.value = false
                        isFound.value = true
                        totalPages = response.body()?.totalPages as Int
                    }
                } else {
                    isLoading.value = false
                    isFound.value = false
                }
            } else {
                isLoading.value = false
                isFound.value = false
            }
        } catch(e:Exception) {
            withContext(Dispatchers.Main) {
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
                getOnAirData()
            }
        } else if(spinner == 3){
            if(pageTP <= totalPages){
                pageTP++
                getTopRated()
            }
        }
    }
}
