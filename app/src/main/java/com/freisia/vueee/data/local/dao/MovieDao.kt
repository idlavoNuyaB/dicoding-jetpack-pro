package com.freisia.vueee.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.freisia.vueee.model.movie.Movie

@Dao
interface MovieDao {
    @Query("Select * FROM Movie ORDER BY Lower(title) ASC")
    fun findMovie() : DataSource.Factory<Int, Movie>

    @Query("Select * FROM Movie ORDER BY Lower(title) ASC")
    fun findAllMovie() : LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie) : Long

    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM Movie")
    suspend fun deleteAll()
}