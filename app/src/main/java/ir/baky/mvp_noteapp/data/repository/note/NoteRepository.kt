package ir.baky.mvp_noteapp.data.repository.note


import ir.baky.mvp_noteapp.data.database.NoteDao
import ir.baky.mvp_noteapp.data.model.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(private val dao: NoteDao){
    fun saveNote(entity: NoteEntity) = dao.saveNote(entity)
    fun detailNote(id: Int) = dao.getNote(id)
    fun updateNote(entity: NoteEntity) = dao.updateNote(entity)

}