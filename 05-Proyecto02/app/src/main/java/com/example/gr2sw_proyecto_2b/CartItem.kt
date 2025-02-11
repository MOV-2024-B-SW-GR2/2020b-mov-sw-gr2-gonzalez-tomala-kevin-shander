package com.example.gr2sw_proyecto_2b

import android.os.Parcelable

class CartItem(
    val id: Int,
    val cartId: Int,
    val productId: Int,
    val quantity: Int
): Parcelable {

    constructor(
        parcel: android.os.Parcel
    ) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return "CartItem(id=$id, cartId=$cartId, productId=$productId, quantity=$quantity)"
    }

    override fun writeToParcel(
        parcel: android.os.Parcel,
        flags: Int
    ) {
        parcel.writeInt(id)
        parcel.writeInt(cartId)
        parcel.writeInt(productId)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartItem> {
        override fun createFromParcel(
            parcel: android.os.Parcel
        ): CartItem {
            return CartItem(parcel)
        }

        override fun newArray(
            size: Int
        ): Array<CartItem?> {
            return arrayOfNulls(size)
        }
    }
}