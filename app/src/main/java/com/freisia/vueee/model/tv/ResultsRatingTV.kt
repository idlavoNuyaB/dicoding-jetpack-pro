package com.freisia.vueee.model.tv

import com.google.gson.annotations.SerializedName

data class ResultsRatingTV (
    @SerializedName("iso_3166_1") val iso_3166_1 : String,
    @SerializedName("rating") val rating : String
)