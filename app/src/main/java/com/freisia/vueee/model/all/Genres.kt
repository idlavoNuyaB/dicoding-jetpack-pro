package com.freisia.vueee.model.all

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genres (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
) : Parcelable