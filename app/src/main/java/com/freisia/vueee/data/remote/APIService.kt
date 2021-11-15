package com.freisia.vueee.data.remote

import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.movie.ResultMovie
import com.freisia.vueee.model.tv.RatingTV
import com.freisia.vueee.model.tv.ResultTV
import com.freisia.vueee.model.tv.TV
import com.freisia.vueee.utils.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET(Constant.GET_MOVIE)
    suspend fun getMovies(@Query("page") page: Int,
                @Query("api_key") api_key: String = Constant.TOKEN_API,
                @Query("language") language: String = Constant.LANGUAGE): Response<ResultMovie>

    @GET(Constant.GET_NOW_PLAYING_MOVIE)
    suspend fun getNowPlayingMovies(@Query("page") page: Int,
                          @Query("api_key") api_key: String = Constant.TOKEN_API,
                          @Query("language") language: String = Constant.LANGUAGE): Response<ResultMovie>

    @GET(Constant.GET_TOP_RATED_MOVIE)
    suspend fun getTopRatedMovies(@Query("page") page: Int,
                                @Query("api_key") api_key: String = Constant.TOKEN_API,
                                @Query("language") language: String = Constant.LANGUAGE): Response<ResultMovie>

    @GET(Constant.GET_TV)
    suspend fun getTV(@Query("page") page: Int,
                @Query("api_key") api_key: String = Constant.TOKEN_API,
                @Query("language") language: String = Constant.LANGUAGE): Response<ResultTV>

    @GET(Constant.GET_ON_AIR_TV)
    suspend fun getOnAirTV(@Query("page") page: Int,
                      @Query("api_key") api_key: String = Constant.TOKEN_API,
                      @Query("language") language: String = Constant.LANGUAGE): Response<ResultTV>

    @GET(Constant.GET_TOP_RATED_TV)
    suspend fun getTopRatedTV(@Query("page") page: Int,
                      @Query("api_key") api_key: String = Constant.TOKEN_API,
                      @Query("language") language: String = Constant.LANGUAGE): Response<ResultTV>

    @GET(Constant.GET_TV_RATING)
    suspend fun getRatingTV(@Path("tv_id") tv_id: Int,
                @Query("api_key") api_key: String = Constant.TOKEN_API,
                @Query("language") language: String = Constant.LANGUAGE): Response<RatingTV>

    @GET(Constant.GET_MOVIE_DETAIL)
    suspend fun getMovieDetail(@Path("movie_id") movie_id: Int,
                @Query("api_key") api_key: String = Constant.TOKEN_API,
                @Query("language") language: String = Constant.LANGUAGE,
                @Query("append_to_response") append_to_response: String = Constant.RELEASE): Response<Movie>

    @GET(Constant.GET_TV_DETAIL)
    suspend fun getTVDetail(@Path("tv_id") tv_id: Int,
                @Query("api_key") api_key: String = Constant.TOKEN_API,
                @Query("language") language: String = Constant.LANGUAGE) : Response<TV>
}