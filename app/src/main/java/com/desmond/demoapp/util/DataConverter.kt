package com.desmond.demoapp.util

import androidx.room.TypeConverter
import com.desmond.demoapp.model.ArtistList
import com.desmond.demoapp.model.CoverImage
import com.google.gson.Gson

class DataConverter {

    @TypeConverter
    fun fromCoverImageList(albums: List<CoverImage>?): String {
        if (albums == null) {
            return ""
        }

        return Gson().toJson(albums)
    }

    @TypeConverter
    fun toCoverImageList(jsonString: String?) : List<CoverImage>? {
        if (jsonString == null) {
            return null
        }
        return Gson().fromJson(jsonString, Array<CoverImage>::class.java).asList()
    }

    @TypeConverter
    fun fromArtistList(albums: List<ArtistList>?): String {
        if (albums == null) {
            return ""
        }

        return Gson().toJson(albums)
    }

    @TypeConverter
    fun toArtistList(jsonString: String?) : List<ArtistList>? {
        if (jsonString == null) {
            return null
        }
        return Gson().fromJson(jsonString, Array<ArtistList>::class.java).asList()
    }

}