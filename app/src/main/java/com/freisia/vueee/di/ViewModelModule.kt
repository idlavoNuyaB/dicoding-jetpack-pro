package com.freisia.vueee.di

import com.freisia.vueee.data.repository.MovieRepository
import com.freisia.vueee.data.repository.TVRepository
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.tv.TV
import com.freisia.vueee.ui.daftar.movies.MoviesViewModel
import com.freisia.vueee.ui.daftar.tvshow.TVShowsViewModel
import com.freisia.vueee.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel{MoviesViewModel(get())}
    viewModel{TVShowsViewModel(get())}
    viewModel{(type: String) ->
        if(type == "movies"){
            DetailViewModel<MovieRepository, Movie>(get())
        } else {
            DetailViewModel<TVRepository, TV>(get())
        }
    }
}