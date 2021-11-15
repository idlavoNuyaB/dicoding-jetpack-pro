package com.freisia.vueee.model.tv

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.freisia.vueee.model.all.Genres
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = "TV")
data class TV (
    @ColumnInfo(name="episode_run_time") @SerializedName("episode_run_time") val episode_run_time : ArrayList<Int>,
    @ColumnInfo(name="first_air_date") @SerializedName("first_air_date") val first_air_date : String,
    @ColumnInfo(name="genres") @SerializedName("genres") val genres : @RawValue ArrayList<Genres>,
    @PrimaryKey @NonNull @ColumnInfo(name="id") @SerializedName("id") val id : Int,
    @ColumnInfo(name="name") @SerializedName("name") val name : String,
    @ColumnInfo(name="overview") @SerializedName("overview") val overview : String,
    @ColumnInfo(name="poster_path") @SerializedName("poster_path") val poster_path : String,
    @ColumnInfo(name="vote_average") @SerializedName("vote_average") val vote_average : Double,
    @ColumnInfo(name="vote_count") @SerializedName("vote_count") val vote_count : Int
) : Parcelable