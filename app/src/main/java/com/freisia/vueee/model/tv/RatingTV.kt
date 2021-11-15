package com.freisia.vueee.model.tv

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RatingTV (
    @SerializedName("results") val results : ArrayList<ResultsRatingTV>,
    @SerializedName("id") val id : Int
) : Parcelable