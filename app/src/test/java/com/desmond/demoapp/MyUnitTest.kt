package com.desmond.demoapp

import com.desmond.demoapp.model.ArtistList
import com.desmond.demoapp.util.Util
import org.junit.Test

import org.junit.Assert.*

class MyUnitTest {

    @Test
    fun testStringFromList() {
        val list1 = listOf(ArtistList("name1"), ArtistList("name2"), ArtistList("name3"))
        val list2 = listOf(ArtistList("name1"), ArtistList("name2"))
        val list3 = listOf(ArtistList("name1"))

        assertEquals("name1, name2, name3", Util.getNamesInLine(list1))
        assertEquals("name1, name2", Util.getNamesInLine(list2))
        assertEquals("name1", Util.getNamesInLine(list3))
        assertEquals("", Util.getNamesInLine(listOf()))
    }
}