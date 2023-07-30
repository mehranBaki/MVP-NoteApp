package ir.baky.mvp_noteapp.data.repository.main


import ir.baky.mvp_noteapp.data.database.NoteDao
import ir.baky.mvp_noteapp.data.model.NoteEntity
import javax.inject.Inject

class MainRepository @Inject constructor(private val dao: NoteDao){
    fun loadAllNotes() = dao.getAllNotes()
    fun deleteNote(entity: NoteEntity) = dao.deleteNote(entity)
    fun filterNote(priority: String) = dao.filterNote(priority)
    fun searchNote(title: String) = dao.searchNote(title)
}