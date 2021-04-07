package com.adnroidapp.amazingappnasa.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.adnroidapp.amazingappnasa.database.dbData.NotesData

@Dao
interface DaoNotes {
    @Insert
    fun addNotes(notes: NotesData)

    @Insert
    fun addListNotes(listNotes: List<NotesData>)

    @Delete
    fun deleteNotes(notes: NotesData)

    @Query("SELECT * FROM tableNotes ORDER BY id")
    fun getAllNotes() : LiveData<List<NotesData>>

    @Query("SELECT * FROM tableNotes WHERE id = :idNotes LIMIT 1")
    fun getNotesForID(idNotes: Int) : LiveData<NotesData>
}