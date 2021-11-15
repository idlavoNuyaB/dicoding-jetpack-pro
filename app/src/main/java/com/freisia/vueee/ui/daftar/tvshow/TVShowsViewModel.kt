package com.freisia.vueee.ui.daftar.tvshow

import android.content.Context
import androidx.lifecycle.ViewModel
import com.freisia.vueee.R
import com.freisia.vueee.model.Movie

class TVShowsViewModel : ViewModel() {
    private val listData : ArrayList<Movie> = ArrayList()

    fun getData(context: Context) : ArrayList<Movie>{
        val idTV = context.resources.getIntArray(R.array.id_tv)
        val titleTV = context.resources.getStringArray(R.array.title_tv)
        val ratingTV = context.resources.getStringArray(R.array.rating_tv)
        val durationTV = context.resources.getStringArray(R.array.duration_tv)
        val reviewTV = context.resources.getIntArray(R.array.review_tv)
        val genreTV = context.resources.getStringArray(R.array.genre_tv)
        val descriptionTV = context.resources.getStringArray(R.array.description_tv)
        val broadcastTV = context.resources.getStringArray(R.array.broadcast_tv)
        val imageTV = context.assets.list("tvshows")
        for((index,tv) in idTV.withIndex()){
            listData.add(Movie(tv, titleTV[index], ratingTV[index],
                imageTV?.get(index) as String, broadcastTV[index], genreTV[index],
                durationTV[index],reviewTV[index],descriptionTV[index]
            ))
        }
        return listData
    }}
