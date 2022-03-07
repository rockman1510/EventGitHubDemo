package com.eventgithubdemo.api.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Event(
    val id: String?,
    val type: String?,
    val actor: Actor?,
    val repo: Repo?,
    val public: Boolean,
    val created_at: String?,
    val org: Org?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Actor::class.java.classLoader),
        parcel.readParcelable(Repo::class.java.classLoader),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readParcelable(Org::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeParcelable(actor, flags)
        parcel.writeParcelable(repo, flags)
        parcel.writeByte(if (public) 1 else 0)
        parcel.writeString(created_at)
        parcel.writeParcelable(org, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}