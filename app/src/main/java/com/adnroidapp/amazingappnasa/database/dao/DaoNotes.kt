package com.adnroidapp.amazingappnasa.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.adnroidapp.amazingappnasa.database.dbData.NotesData

@Dao
interface DaoNotes {
    @Insert
    fun addNotes(notes: NotesData)

    @Delete
    fun deleteNotes(notes: NotesData)

    @Query("SELECT * FROM tableNotes")
    fun getAllNotes() : NotesData
}