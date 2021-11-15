package com.freisia.vueee.data.repository

import com.freisia.vueee.data.remote.APIService
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.movie.ResultMovie
import com.freisia.vueee.utils.EspressoIdlingResource
import retrofit2.Response

class MovieRepository(private val apiService: APIService){

    suspend fun getDetail(id: Int) : Response<Movie> {
        EspressoIdlingResource.increment()
        val api = apiService.getMovieDetail(id)
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
        return api
    }

    suspend fun getList(page: Int) : Response<ResultMovie>{
        EspressoIdlingResource.increment()
        val api = apiService.getMovies(page)
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
        return api
    }
}