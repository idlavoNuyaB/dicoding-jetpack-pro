package com.freisia.vueee.data.local.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.freisia.vueee.data.local.MovieDBDatabase
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.utils.EspressoIdlingResource

class MovieLocalRepository(private val movieDBDatabase: MovieDBDatabase) {

    fun getDatas() : DataSource.Factory<Int,Movie>{
        EspressoIdlingResource.increment()
        val res = movieDBDatabase.movieDao().findMovie()
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
        return res
    }

    fun getDataDetails() : LiveData<List<Movie>>
    {
        EspressoIdlingResource.increment()
        val res = movieDBDatabase.movieDao().findAllMovie()
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
        return res
    }

    suspend fun insert(movie: Movie) : Long {
        EspressoIdlingResource.increment()
        val res = movieDBDatabase.movieDao().insert(movie)
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
        return res
    }

    suspend fun delete(movie: Movie) {
        EspressoIdlingResource.increment()
        movieDBDatabase.movieDao().delete(movie)
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
    }

    suspend fun deleteAll() {
        EspressoIdlingResource.increment()
        movieDBDatabase.movieDao().deleteAll()
        if(!EspressoIdlingResource.getEspressoIdlingResourceForMainActivity().isIdleNow){
            EspressoIdlingResource.decrement()
        }
    }
}