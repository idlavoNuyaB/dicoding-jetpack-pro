package com.freisia.vueee.model.movie

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Releases (
    @SerializedName("countries") val countries : ArrayList<Countries>
): Parcelable