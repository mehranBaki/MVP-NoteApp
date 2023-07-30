package ir.baky.mvp_noteapp.ui.main

import ir.baky.mvp_noteapp.data.model.NoteEntity
import ir.baky.mvp_noteapp.ui.base.BasePresenter

interface MainContracts {

    interface View{
        fun showAllNotes(notes: List<NoteEntity>)
        fun showEmpty()
        fun deleteMessage()
    }

    interface Presenter : BasePresenter{
        fun loadAllNotes()
        fun deleteNote(entity: NoteEntity)
        fun filterNote(priority: String)
        fun searchNote(title: String)
    }
}