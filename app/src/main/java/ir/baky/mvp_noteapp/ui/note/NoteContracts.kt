package ir.baky.mvp_noteapp.ui.note

import ir.baky.mvp_noteapp.data.model.NoteEntity
import ir.baky.mvp_noteapp.ui.base.BasePresenter


interface NoteContracts {

    interface View{
        fun close()
        fun loadNote(note: NoteEntity)
    }

    interface Presenter : BasePresenter {
        fun saveNote(entity: NoteEntity)
        fun detailNote(id: Int)
        fun updateNote(entity: NoteEntity)
    }

}