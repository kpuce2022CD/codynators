package com.example.codrum.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SongDao {

    @Query("SELECT * FROM Song")
    fun getAll(): LiveData<List<Song>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(song: Song)

    @Delete
    fun delete(song: Song)
}