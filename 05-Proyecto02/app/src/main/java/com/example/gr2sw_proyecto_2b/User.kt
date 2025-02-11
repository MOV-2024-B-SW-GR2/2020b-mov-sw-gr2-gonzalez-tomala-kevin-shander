package com.example.gr2sw_proyecto_2b

import android.os.Parcel
import android.os.Parcelable

class User(
    val uid: String,
    val email: String,
    val displayName: String,
): Parcelable {
    val id: String
        get() = uid

    val correo: String
        get() = email

    val nombre: String
        get() = displayName

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun toString(): String {
        return displayName
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(email)
        parcel.writeString(displayName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}