package com.desmond.demoapp.model

import android.os.Parcel
import android.os.Parcelable

class Album(
    var type: String?,
    var release_date: String?,
    var name: String?,
    var id: String?,
    var album_type: String?,
    var total_tracks: Int,
    var images: List<Cover>?,
    var artists:List<Artist>?
) : Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        arrayListOf<Cover>().apply {
            parcel.readList(this, Cover::class.java.classLoader)
        },
        arrayListOf<Artist>().apply {
            parcel.readList(this, Artist::class.java.classLoader)
        })

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(release_date)
        parcel.writeString(name)
        parcel.writeString(id)
        parcel.writeString(album_type)
        parcel.writeInt(total_tracks)
        parcel.writeList(images)
        parcel.writeList(artists)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }
    }
}