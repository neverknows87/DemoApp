package com.desmond.demoapp.database

import androidx.lifecycle.LiveData
import com.desmond.demoapp.model.Album

class AlbumRepository(private val albumDao: AlbumDao) {

    val allAlbums: LiveData<List<Album>> = albumDao.getAlbumList()

    suspend fun insert(albums: List<Album>) {
        albumDao.deleteAll()
        albumDao.insert(albums)
    }
}