package ir.baky.mvp_noteapp.ui.note

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.baky.mvp_noteapp.data.model.NoteEntity
import ir.baky.mvp_noteapp.data.repository.note.NoteRepository
import ir.baky.mvp_noteapp.ui.base.BasePresenterImpl
import javax.inject.Inject

class NotePresenter @Inject constructor(private val repository: NoteRepository, private val view: NoteContracts.View): NoteContracts.Presenter, BasePresenterImpl(){

    override fun saveNote(entity: NoteEntity) {
        disposable = repository.saveNote(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                view.close()
            }
    }

    override fun detailNote(id: Int) {
        disposable = repository.detailNote(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                view.loadNote(it)
            }
    }

    override fun updateNote(entity: NoteEntity) {
        disposable = repository.updateNote(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                view.close()
            }
    }


}