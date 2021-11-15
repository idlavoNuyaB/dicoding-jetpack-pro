package com.freisia.vueee.model.movie

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Countries (
    @SerializedName("certification") val certification : String,
    @SerializedName("iso_3166_1") val iso_3166_1 : String,
    @SerializedName("primary") val primary : Boolean,
    @SerializedName("release_date") val release_date : String
) : Parcelable