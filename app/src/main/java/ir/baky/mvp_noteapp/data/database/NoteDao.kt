package ir.baky.mvp_noteapp.data.database

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import ir.baky.mvp_noteapp.data.model.NoteEntity
import ir.baky.mvp_noteapp.utils.NOTE_TABLE


@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNote(entity: NoteEntity) : Completable

    @Delete
    fun deleteNote(entity: NoteEntity): Completable

    @Update
    fun updateNote(entity: NoteEntity): Completable

    @Query("SELECT * FROM $NOTE_TABLE")
    fun getAllNotes(): Observable<List<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE id == :id")
    fun getNote(id: Int): Observable<NoteEntity>

    @Query("SELECT * FROM $NOTE_TABLE WHERE priority == :priority")
    fun filterNote(priority: String) : Observable<List<NoteEntity>>

    @Query("SELECT * FROM $NOTE_TABLE WHERE title LIKE '%' || :title || '%' ")
    fun searchNote(title: String) : Observable<List<NoteEntity>>

}