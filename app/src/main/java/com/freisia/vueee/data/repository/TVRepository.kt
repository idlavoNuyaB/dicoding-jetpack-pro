package com.freisia.vueee.data.repository

import com.freisia.vueee.data.remote.APIService
import com.freisia.vueee.model.tv.RatingTV
import com.freisia.vueee.model.tv.ResultTV
import com.freisia.vueee.model.tv.TV
import com.freisia.vueee.utils.EspressoIdlingResource
import retrofit2.Response

class TVRepository(private val apiService: APIService){

    suspend fun getDetail(id: Int): Response<TV> {
        EspressoIdlingResource.increment()
        val api = apiService.getTVDetail(id)
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
        return api
    }

    suspend fun getList(page: Int) : Response<ResultTV> {
        EspressoIdlingResource.increment()
        val api = apiService.getTV(page)
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
        return api
    }

    suspend fun getRating(id: Int) : Response<RatingTV> {
        EspressoIdlingResource.increment()
        val api = apiService.getRatingTV(id)
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
        return api
    }

}