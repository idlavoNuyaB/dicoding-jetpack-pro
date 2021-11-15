package com.freisia.vueee.model.tv

import android.os.Parcelable
import com.freisia.vueee.model.all.Genres
import com.freisia.vueee.model.all.Production_companies
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class TV (
    @SerializedName("backdrop_path") val backdrop_path : String,
    @SerializedName("created_by") val created_by : @RawValue ArrayList<Created_by>,
    @SerializedName("episode_run_time") val episode_run_time : List<Int>,
    @SerializedName("first_air_date") val first_air_date : String,
    @SerializedName("genres") val genres : @RawValue ArrayList<Genres>,
    @SerializedName("homepage") val homepage : String,
    @SerializedName("id") val id : Int,
    @SerializedName("in_production") val in_production : Boolean,
    @SerializedName("languages") val languages : ArrayList<String>,
    @SerializedName("last_air_date") val last_air_date : String,
    @SerializedName("last_episode_to_air") val last_episode_to_air : @RawValue Last_episode_to_air,
    @SerializedName("name") val name : String,
    @SerializedName("next_episode_to_air") val next_episode_to_air : @RawValue Next_episode_to_air,
    @SerializedName("networks") val networks : @RawValue ArrayList<Networks>,
    @SerializedName("number_of_episodes") val number_of_episodes : Int,
    @SerializedName("number_of_seasons") val number_of_seasons : Int,
    @SerializedName("origin_country") val origin_country : ArrayList<String>,
    @SerializedName("original_language") val original_language : String,
    @SerializedName("original_name") val original_name : String,
    @SerializedName("overview") val overview : String,
    @SerializedName("popularity") val popularity : Double,
    @SerializedName("poster_path") val poster_path : String,
    @SerializedName("production_companies") val production_companies : @RawValue ArrayList<Production_companies>,
    @SerializedName("seasons") val seasons : @RawValue ArrayList<Seasons>,
    @SerializedName("status") val status : String,
    @SerializedName("tagline") val tagline : String,
    @SerializedName("type") val type : String,
    @SerializedName("vote_average") val vote_average : Double,
    @SerializedName("vote_count") val vote_count : Int
) : Parcelable