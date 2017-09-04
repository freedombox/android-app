/*
 * This file is part of FreedomBox.
 *
 * FreedomBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FreedomBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FreedomBox. If not, see <http://www.gnu.org/licenses/>.
 */

package org.freedombox.freedombox.views.model

import android.os.Parcel
import android.os.Parcelable

data class ConfigModel( val boxName: String,
                        val domain: String,
                        val username: String,
                        val password: String,
                        val default: Boolean): Parcelable{
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