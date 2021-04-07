package com.adnroidapp.amazingappnasa.database.dbData

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adnroidapp.amazingappnasa.database.TABLE_DB_NOTES
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TABLE_DB_NOTES)
@Parcelize
data class NotesData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nameNotes: String,
    val message: String
) : Parcelable