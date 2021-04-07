package com.adnroidapp.amazingappnasa.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotesData (val id: Int, val nameNotes: String, val message: String) : Parcelable