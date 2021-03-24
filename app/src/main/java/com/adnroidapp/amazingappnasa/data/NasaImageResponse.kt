package com.adnroidapp.amazingappnasa.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NasaImageResponse(
    @Expose val date: String? = null,
    @Expose val mediaType: String? = null,
    @Expose val hdurl: String? = null,
    @Expose val serviceVersion: String? = null,
    @Expose val explanation: String? = null,
    @Expose val title: String? = null,
    @Expose val url: String? = null
) : Parcelable
