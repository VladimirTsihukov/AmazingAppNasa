package com.adnroidapp.amazingappnasa.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoRoverInMars(
	@Expose val photos: List<PhotosItem>
) : Parcelable

@Parcelize
data class PhotosItem(
	@Expose val earthDate: String,
	@Expose val rover: Rover,
	@Expose val imgSrc: String
) : Parcelable

@Parcelize
data class Rover(
	@Expose val name: String,
	@Expose val launchDate: String,
	@Expose val landingDate: String,
) : Parcelable

