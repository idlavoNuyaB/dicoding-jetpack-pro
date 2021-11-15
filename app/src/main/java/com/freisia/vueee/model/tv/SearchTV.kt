package com.freisia.vueee.model.tv

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchTV (
    @SerializedName("name") val name : String,
    @SerializedName("id") val id : Int,
    @SerializedName("vote_average") val vote_average : Double,
    @SerializedName("poster_path") val poster_path : String
) : Parcelable