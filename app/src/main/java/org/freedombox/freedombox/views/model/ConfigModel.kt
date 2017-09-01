package org.freedombox.freedombox.views.model

import android.os.Parcel
import android.os.Parcelable

data class ConfigModel( var boxName: String,
                        var domain: String,
                        var username: String,
                        var password: String,
                        var default: Boolean): Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(boxName)
        parcel.writeString(domain)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeByte(if (default) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConfigModel> {
        override fun createFromParcel(parcel: Parcel): ConfigModel {
            return ConfigModel(parcel)
        }

        override fun newArray(size: Int): Array<ConfigModel?> {
            return arrayOfNulls(size)
        }
    }

}