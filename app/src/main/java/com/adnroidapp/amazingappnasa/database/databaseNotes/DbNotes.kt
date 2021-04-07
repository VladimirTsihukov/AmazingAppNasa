package com.adnroidapp.amazingappnasa.database.databaseNotes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adnroidapp.amazingappnasa.database.DATABASE_NAME_NOTES
import com.adnroidapp.amazingappnasa.database.dao.DaoNotes
import com.adnroidapp.amazingappnasa.database.dbData.NotesData

@Database(entities = [NotesData::class], version = 1)
abstract class DbNotes : RoomDatabase() {

    abstract fun notes(): DaoNotes

    companion object {
        private var dbNotes: DbNotes? = null
        private val LOCK = Any()

        fun instance(context: Context) : DbNotes {
            synchronized(LOCK) {
                dbNotes?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    DbNotes::class.java,
                    DATABASE_NAME_NOTES
                ).fallbackToDestructiveMigration().build()
                dbNotes = instance
                return instance
            }
        }
    }
}