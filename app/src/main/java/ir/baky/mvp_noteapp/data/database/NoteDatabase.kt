package ir.baky.mvp_noteapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.baky.mvp_noteapp.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao(): NoteDao
}