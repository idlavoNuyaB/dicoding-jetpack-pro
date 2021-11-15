package com.freisia.vueee.model.tv

import com.google.gson.annotations.SerializedName

data class RatingTV (
    @SerializedName("results") val results : ArrayList<ResultsRatingTV>,
    @SerializedName("id") val id : Int
)