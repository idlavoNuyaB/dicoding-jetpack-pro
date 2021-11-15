package com.freisia.vueee.model.movie

import com.google.gson.annotations.SerializedName

data class Belongs_to_Collection (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("poster_path") val poster_path : Boolean,
    @SerializedName("backdrop_path") val backdrop_path : String
)