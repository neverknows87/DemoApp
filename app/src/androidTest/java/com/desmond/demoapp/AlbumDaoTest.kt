package com.desmond.demoapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.desmond.demoapp.database.AlbumDao
import com.desmond.demoapp.database.AlbumRoomDb
import com.desmond.demoapp.model.Album
import com.desmond.demoapp.model.ArtistList
import com.desmond.demoapp.model.CoverImage
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AlbumDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var albumDao: AlbumDao
    private lateinit var db: AlbumRoomDb

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, AlbumRoomDb::class.java)
            .allowMainThreadQueries()
            .build()
        albumDao = db.albumDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAlbumList() = runBlocking {
        val albumList = getTestAlbumList()
        albumDao.insert(albumList)
        val allAlbums = albumDao.getAlbumList().waitForValue()
        assertEquals(allAlbums[1].name, albumList[1].name)
    }

    @Test
    @Throws(Exception::class)
    fun getAllAlbums() = runBlocking {
        val albumList = getTestAlbumList()
        albumDao.insert(albumList)
        val cachedAlbum = albumDao.getAlbumList().waitForValue()
        assertEquals(cachedAlbum[0].name, albumList[0].name)
        assertEquals(cachedAlbum[1].name, albumList[1].name)
    }

    @Test
    @Throws(Exception::class)
    fun testArray() = runBlocking {
        val albumList = getTestAlbumList()
        albumDao.insert(albumList)
        val cachedAlbum = albumDao.getAlbumList().waitForValue()
        assertEquals(cachedAlbum[0].images?.get(0)?.url, albumList[0].images?.get(0)?.url)
        assertEquals(cachedAlbum[0].artists?.get(0)?.name, albumList[0].artists?.get(0)?.name)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val albumList = getTestAlbumList()
        albumDao.insert(albumList)
        albumDao.deleteAll()
        val cachedAlbum = albumDao.getAlbumList().waitForValue()
        assertTrue(cachedAlbum.isEmpty())
    }

    fun getTestAlbumList(): List<Album> {
        val coverImageList = listOf(CoverImage("url1"), CoverImage("url2"))
        val artistList = listOf(ArtistList("artist1"), ArtistList("artist2"), ArtistList("artist3"))
        val album1 = Album(1, "2019-11-06", "Test Album", 3, coverImageList, artistList)
        val album2 = Album(2, "2018-10-06", "Test Album2", 1, coverImageList, artistList)
        return listOf(album1, album2)
    }
}