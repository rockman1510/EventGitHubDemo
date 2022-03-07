package com.eventgithubdemo.api.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Org(
    val id: Long,
    val login: String?,
    val gravatar_id: String?,
    val url: String?,
    val avatar_url: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(login)
        parcel.writeString(gravatar_id)
        parcel.writeString(url)
        parcel.writeString(avatar_url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Org> {
        override fun createFromParcel(parcel: Parcel): Org {
            return Org(parcel)
        }

        override fun newArray(size: Int): Array<Org?> {
            return arrayOfNulls(size)
        }
    }
}