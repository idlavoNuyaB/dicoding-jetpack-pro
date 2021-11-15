package com.freisia.vueee.model.movie

import android.os.Parcelable
import com.freisia.vueee.model.all.Genres
import com.freisia.vueee.model.all.Production_companies
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Movie (
    @SerializedName("adult") val adult : Boolean,
    @SerializedName("backdrop_path") val backdrop_path : String,
    @SerializedName("belongs_to_collection") val belongs_to_collection : @RawValue Belongs_to_Collection,
    @SerializedName("budget") val budget : Int,
    @SerializedName("genres") val genres : @RawValue ArrayList<Genres>,
    @SerializedName("homepage") val homepage : String,
    @SerializedName("id") val id : Int,
    @SerializedName("imdb_id") val imdb_id : String,
    @SerializedName("original_language") val original_language : String,
    @SerializedName("original_title") val original_title : String,
    @SerializedName("overview") val overview : String,
    @SerializedName("popularity") val popularity : Double,
    @SerializedName("poster_path") val poster_path : String,
    @SerializedName("production_companies") val production_companies : @RawValue ArrayList<Production_companies>,
    @SerializedName("production_countries") val production_countries : @RawValue ArrayList<Production_countries>,
    @SerializedName("release_date") val release_date : String,
    @SerializedName("revenue") val revenue : Int,
    @SerializedName("runtime") val runtime : Int,
    @SerializedName("spoken_languages") val spoken_languages : @RawValue ArrayList<Spoken_languages>,
    @SerializedName("status") val status : String,
    @SerializedName("tagline") val tagline : String,
    @SerializedName("title") val title : String,
    @SerializedName("video") val video : Boolean,
    @SerializedName("vote_average") val vote_average : Double,
    @SerializedName("vote_count") val vote_count : Int,
    @SerializedName("releases") val releases : @RawValue Releases
) : Parcelable