package com.freisia.vueee.ui.detail

import androidx.lifecycle.ViewModel
import com.freisia.vueee.model.Movie

class DetailViewModel : ViewModel(){
    fun getData(movie: Movie): Movie = movie
}