package com.freisia.vueee.ui.daftar.tvshow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freisia.vueee.data.repository.TVRepository
import com.freisia.vueee.model.tv.SearchTV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TVShowsViewModel(private val repository: TVRepository) : ViewModel() {
    var listData = MutableLiveData<List<SearchTV>>()
    var isLoading = MutableLiveData<Boolean>()
    var isFound = MutableLiveData<Boolean>()
    private var page = 1
    private var totalPages = 500

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

    fun onLoadMore(){
        page++
        if(page <= totalPages){
            getData()
        }
    }
}
