package com.freisia.vueee.model.movie

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchMovie (
    @SerializedName("poster_path") val poster_path : String,
    @SerializedName("id") val id : Int,
    @SerializedName("backdrop_path") val backdrop_path : String,
    @SerializedName("title") val title : String,
    @SerializedName("vote_average") val vote_average : Double,
) : Parcelable