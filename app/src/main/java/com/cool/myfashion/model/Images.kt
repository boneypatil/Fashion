package com.cool.myfashion.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by rahul.p
 *
 */
data class Images(

	val url: String?,
	val width: Int,
	val height: Int,
	val format: String?
):Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readInt(),
		parcel.readInt(),
		parcel.readString()
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(url)
		parcel.writeInt(width)
		parcel.writeInt(height)
		parcel.writeString(format)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Images> {
		override fun createFromParcel(parcel: Parcel): Images {
			return Images(parcel)
		}

		override fun newArray(size: Int): Array<Images?> {
			return arrayOfNulls(size)
		}
	}
}