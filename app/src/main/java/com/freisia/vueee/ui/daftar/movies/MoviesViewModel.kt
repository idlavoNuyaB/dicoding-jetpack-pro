package com.freisia.vueee.ui.daftar.movies

import android.content.Context
import androidx.lifecycle.ViewModel
import com.freisia.vueee.R
import com.freisia.vueee.model.Movie

class MoviesViewModel : ViewModel() {
    private val listData : ArrayList<Movie> = ArrayList()

    fun getData(context: Context) : ArrayList<Movie>{
        val idMovies = context.resources.getIntArray(R.array.id_movie)
        val titleMovies = context.resources.getStringArray(R.array.title_movie)
        val ratingMovies = context.resources.getStringArray(R.array.rating_movie)
        val durationMovies = context.resources.getStringArray(R.array.duration_movie)
        val reviewMovies = context.resources.getIntArray(R.array.review_movie)
        val genreMovies = context.resources.getStringArray(R.array.genre_movie)
        val descriptionMovies = context.resources.getStringArray(R.array.description_movie)
        val broadcastMovies = context.resources.getStringArray(R.array.broadcast_movie)
        val imageMovies = context.assets.list("movies")
        for((index,movie) in idMovies.withIndex()){
            listData.add(Movie(movie, titleMovies[index], ratingMovies[index],
                imageMovies?.get(index) as String, broadcastMovies[index], genreMovies[index],
                durationMovies[index],reviewMovies[index],descriptionMovies[index]
            ))
        }
        return listData
    }
}
