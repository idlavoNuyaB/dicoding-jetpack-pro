package com.freisia.vueee.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.freisia.vueee.model.tv.TV

@Dao
interface TVDao {
    @Query("Select * FROM TV ORDER BY Lower(name) ASC")
    fun findTV() : DataSource.Factory<Int, TV>

    @Query("Select * FROM TV ORDER BY Lower(name) ASC")
    fun findAllTV() : LiveData<List<TV>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tv: TV) : Long

    @Delete
    suspend fun delete(tv: TV)

    @Query("DELETE FROM TV")
    suspend fun deleteAll()
}