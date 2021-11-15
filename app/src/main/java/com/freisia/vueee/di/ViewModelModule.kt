package com.freisia.vueee.di

import com.freisia.vueee.data.local.repository.MovieLocalRepository
import com.freisia.vueee.data.local.repository.TVLocalRepository
import com.freisia.vueee.data.remote.repository.MovieRepository
import com.freisia.vueee.data.remote.repository.TVRepository
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.tv.TV
import com.freisia.vueee.ui.list.movies.MoviesViewModel
import com.freisia.vueee.ui.list.tvshow.TVShowsViewModel
import com.freisia.vueee.ui.detail.DetailViewModel
import com.freisia.vueee.ui.favorite.movies.MoviesFavoriteViewModel
import com.freisia.vueee.ui.favorite.tvshow.TVShowsFavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel{MoviesViewModel(get())}
    viewModel{TVShowsViewModel(get())}
    viewModel{(type: String) ->
        if(type == "movies" || type == "localmovies"){
            DetailViewModel<MovieRepository, Movie, MovieLocalRepository>(get(),get())
        } else {
            DetailViewModel<TVRepository, TV, TVLocalRepository>(get(),get())
        }
    }
    viewModel{MoviesFavoriteViewModel(get())}
    viewModel{TVShowsFavoriteViewModel(get())}
}