package com.freisia.vueee.data.local.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.freisia.vueee.data.local.MovieDBDatabase
import com.freisia.vueee.data.local.dao.TVDao
import com.freisia.vueee.model.tv.TV
import com.freisia.vueee.utils.EspressoIdlingResource

class TVLocalRepository(private val movieDBDatabase: MovieDBDatabase) {

    fun getDatas() : DataSource.Factory<Int,TV>{
        EspressoIdlingResource.increment()
        val data = movieDBDatabase.tvDao().findTV()
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
        return data
    }

    fun getDataDetail() : LiveData<List<TV>> {
        EspressoIdlingResource.increment()
        val data = movieDBDatabase.tvDao().findAllTV()
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
        return data
    }

    suspend fun insert(tv: TV) : Long {
        EspressoIdlingResource.increment()
        val data = movieDBDatabase.tvDao().insert(tv)
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
        return data
    }
    suspend fun delete(tv: TV) {
        EspressoIdlingResource.increment()
        movieDBDatabase.tvDao().delete(tv)
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
    }
    suspend fun deleteAll() {
        EspressoIdlingResource.increment()
        movieDBDatabase.tvDao().deleteAll()
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
    }
}