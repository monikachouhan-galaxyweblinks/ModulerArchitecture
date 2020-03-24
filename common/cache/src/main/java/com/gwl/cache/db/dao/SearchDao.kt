package com.gwl.cache.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gwl.model.SearchHistory

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(history: SearchHistory)

    @Update
    suspend fun update(history: SearchHistory): Int

    @Delete
    suspend fun delete(history: SearchHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(histories: List<SearchHistory>)

    @Query("SELECT * FROM searchhistory")
    fun getAllSearchHistory(): LiveData<List<SearchHistory>>

    @Query("SELECT * FROM searchhistory")
    fun getSearchHistories(): List<SearchHistory>
}