package com.adnroidapp.amazingappnasa.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageEarthResponse(
	@Expose
	val response: List<ResponseImageEarth>
) : Parcelable


@Parcelize
data class ResponseImageEarth(
	@Expose val image: String,
	@Expose val date: String,
) : Parcelable

