package com.example.gr2sw_proyecto_2b

import android.os.Parcelable

class Cart(
    val id: Int,
    val userId: Int,
    val total: Double,
    val deliveryPayment: Double,
    val state: Boolean
): Parcelable {

    constructor(parcel: android.os.Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun toString(): String {
        return "Cart(id=$id, userId=$userId, total=$total, deliveryPayment=$deliveryPayment)"
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(userId)
        parcel.writeDouble(total)
        parcel.writeDouble(deliveryPayment)
        parcel.writeByte(if (state) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cart> {
        override fun createFromParcel(parcel: android.os.Parcel): Cart {
            return Cart(parcel)
        }

        override fun newArray(size: Int): Array<Cart?> {
            return arrayOfNulls(size)
        }
    }
}