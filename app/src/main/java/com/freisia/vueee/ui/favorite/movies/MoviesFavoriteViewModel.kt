package com.freisia.vueee.ui.favorite.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.freisia.vueee.data.local.repository.MovieLocalRepository
import com.freisia.vueee.model.movie.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesFavoriteViewModel(private val repository: MovieLocalRepository) : ViewModel() {
    fun getData() : LiveData<PagedList<Movie>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(repository.getDatas(),config).build()
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}
