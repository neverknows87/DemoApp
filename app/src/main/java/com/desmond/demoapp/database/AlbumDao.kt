package com.desmond.demoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.desmond.demoapp.model.Album

@Dao
interface AlbumDao {
    @Query("SELECT * from album_table")
    fun getAlbumList(): LiveData<List<Album>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(albums: List<Album>)

    @Query("DELETE FROM album_table")
    suspend fun deleteAll()
}