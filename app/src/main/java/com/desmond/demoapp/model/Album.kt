package com.desmond.demoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.desmond.demoapp.util.DataConverter
import java.io.Serializable

@Entity(tableName = "album_table")
@TypeConverters(DataConverter::class)
data class Album(
    @PrimaryKey(autoGenerate = true)
    var _id: Int,
    var release_date: String?,
    var name: String?,
    var total_tracks: Int,
    var images: List<CoverImage>,
    var artists:List<ArtistList>
) : Serializable

@Entity
data class CoverImage(var url: String?) : Serializable
@Entity
data class ArtistList(var name: String?) : Serializable