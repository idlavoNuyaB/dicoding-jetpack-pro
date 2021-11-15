package com.freisia.vueee.model.tv

import com.google.gson.annotations.SerializedName

data class Created_by (

    @SerializedName("id") val id : Int,
    @SerializedName("credit_id") val credit_id : String,
    @SerializedName("name") val name : String,
    @SerializedName("gender") val gender : Int,
    @SerializedName("profile_path") val profile_path : String
)