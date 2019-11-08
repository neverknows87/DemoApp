package com.desmond.demoapp.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.desmond.demoapp.database.AlbumRepository
import com.desmond.demoapp.database.AlbumRoomDb
import com.desmond.demoapp.model.Album
import kotlinx.coroutines.launch

class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AlbumRepository

    val allAlbums: LiveData<List<Album>>

    init {
        val albumDao = AlbumRoomDb.getDatabase(application, viewModelScope).albumDao()
        repository = AlbumRepository(albumDao)
        allAlbums = repository.allAlbums
    }

    fun insert(albums: List<Album>) = viewModelScope.launch {
        repository.insert(albums)
    }
}