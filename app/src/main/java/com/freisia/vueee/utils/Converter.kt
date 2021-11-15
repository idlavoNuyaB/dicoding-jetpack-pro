package com.freisia.vueee.utils

import androidx.room.TypeConverter
import com.freisia.vueee.model.all.Genres
import com.freisia.vueee.model.movie.Releases
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun fromGenreToString(value: ArrayList<Genres>) : String{
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromStringToGenre(value: String): ArrayList<Genres>{
        val listType = object : TypeToken<ArrayList<Genres>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromReleasetoString(value: Releases): String{
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromStringToRelease(value: String): Releases{
        val listType = object : TypeToken<Releases>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromIntToString(value: ArrayList<Int>) : String{
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromStringToInt(value: String): ArrayList<Int>{
        val listType = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }
}