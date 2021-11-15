package com.freisia.vueee.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.freisia.vueee.data.local.repository.TVLocalRepository
import com.freisia.vueee.model.tv.TV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TVShowsFavoriteViewModel(private val repository: TVLocalRepository) : ViewModel() {
    fun getData() : LiveData<PagedList<TV>> = LivePagedListBuilder(repository.getDatas(),20).build()

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}
