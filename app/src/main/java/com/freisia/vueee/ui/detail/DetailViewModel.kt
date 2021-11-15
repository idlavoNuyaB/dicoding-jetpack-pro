package com.freisia.vueee.ui.detail

import androidx.lifecycle.*
import com.freisia.vueee.data.local.repository.MovieLocalRepository
import com.freisia.vueee.data.local.repository.TVLocalRepository
import com.freisia.vueee.data.remote.repository.MovieRepository
import com.freisia.vueee.data.remote.repository.TVRepository
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.tv.TV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel <T,Z,K>(private val repository: T,private val localRepo: K) : ViewModel(){
    var listData = MutableLiveData<Z>()
    var isLoading = MutableLiveData<Boolean>()
    var isFound = MutableLiveData<Boolean>()
    var rate = MutableLiveData<String>()
    val localData : LiveData<List<Z>>?

    init{
        localData = getLocalData()
        isLoading.value =false
        isFound.value = true
    }

    @JvmName("getLocalData1")
    private fun getLocalData() :LiveData<List<Z>>?{
        return when (localRepo) {
            is TVLocalRepository -> {
                localRepo.getDataDetail() as LiveData<List<Z>>?
            }
            is MovieLocalRepository -> {
                localRepo.getDataDetails() as LiveData<List<Z>>?
            }
            else -> {
                val repo = localRepo as TVLocalRepository
                repo.getDataDetail() as LiveData<List<Z>>?
            }
        }
    }

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

    fun insert(data: Z){
        if(data is Movie){
            viewModelScope.launch(Dispatchers.IO){
                if(localRepo is MovieLocalRepository){
                    localRepo.insert(data)
                }
            }
        }
        else if(data is TV) {
            viewModelScope.launch(Dispatchers.IO){
                if(localRepo is TVLocalRepository){
                    localRepo.insert(data)
                }
            }
        }
    }

    fun delete(data: Z) {
        if (data is Movie) {
            viewModelScope.launch(Dispatchers.IO) {
                if (localRepo is MovieLocalRepository) {
                    localRepo.delete(data)
                }
            }
        } else if (data is TV) {
            viewModelScope.launch(Dispatchers.IO) {
                if (localRepo is TVLocalRepository) {
                    localRepo.delete(data)
                }
            }
        }
    }
}