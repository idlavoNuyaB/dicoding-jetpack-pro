package com.freisia.vueee.model.tv

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultsRatingTV (
    @SerializedName("iso_3166_1") val iso_3166_1 : String,
    @SerializedName("rating") val rating : String
) : Parcelable