package com.freisia.vueee.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("picture") val picture: String,
    @SerializedName("broadcast") val broadcast: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("duration") val durations: String,
    @SerializedName("review") val review: Int,
    @SerializedName("description") val description: String
) : Parcelable