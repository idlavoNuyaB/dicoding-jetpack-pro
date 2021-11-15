package com.freisia.vueee.model.movie

import com.google.gson.annotations.SerializedName

data class Releases (

    @SerializedName("countries") val countries : ArrayList<Countries>
)