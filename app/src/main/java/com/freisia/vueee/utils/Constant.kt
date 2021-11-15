package com.freisia.vueee.utils

import com.freisia.vueee.BuildConfig

class Constant {
    companion object{
        const val TOKEN_API = BuildConfig.TOKEN_API
        const val BASE_URL = "https://api.themoviedb.org"
        const val GET_MOVIE = "/3/movie/popular"
        const val GET_TV = "/3/tv/popular"
        const val GET_MOVIE_DETAIL = "/3/movie/{movie_id}"
        const val GET_TV_DETAIL = "/3/tv/{tv_id}"
        const val GET_TV_RATING = "3/tv/{tv_id}/content_ratings"
        const val RELEASE = "releases"
        const val LANGUAGE = "en-us"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original/"
    }
}