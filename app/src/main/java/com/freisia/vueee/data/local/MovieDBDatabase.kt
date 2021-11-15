package com.freisia.vueee.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.freisia.vueee.data.local.dao.MovieDao
import com.freisia.vueee.data.local.dao.TVDao
import com.freisia.vueee.model.movie.Movie
import com.freisia.vueee.model.tv.TV
import com.freisia.vueee.utils.Converter

@Database(entities = [Movie::class, TV::class],version = 1,exportSchema = false)
@TypeConverters(Converter::class)
abstract class MovieDBDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TVDao
}