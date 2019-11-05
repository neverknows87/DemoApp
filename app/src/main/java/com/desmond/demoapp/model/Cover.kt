package com.desmond.demoapp.model

import android.os.Parcel
import android.os.Parcelable

class Cover(var height: Int, var width: Int, var url: String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(height)
        dest.writeInt(width)
        dest.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cover> {
        override fun createFromParcel(parcel: Parcel): Cover {
            return Cover(parcel)
        }

        override fun newArray(size: Int): Array<Cover?> {
            return arrayOfNulls(size)
        }
    }
}