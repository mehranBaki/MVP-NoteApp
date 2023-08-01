package ir.baky.mvp_noteapp.ui.main

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.baky.mvp_noteapp.data.model.NoteEntity
import ir.baky.mvp_noteapp.data.repository.main.MainRepository
import ir.baky.mvp_noteapp.ui.base.BasePresenterImpl
import javax.inject.Inject

class MainPresenter @Inject constructor(private val repository: MainRepository, private val view:MainContracts.View): MainContracts.Presenter, BasePresenterImpl(){
    override fun loadAllNotes() {
        disposable.set(
            repository.loadAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isNotEmpty()) {
                        //Log.e("Method: ","loadAllNotes")
                        view.showAllNotes(it)
                    }else{
                        view.showEmpty()
                    }
                }
        )
    }

    override fun deleteNote(entity: NoteEntity) {
        disposable.set(
            repository.deleteNote(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                //Log.e("Method: ","deleteNote")
                view.deleteMessage()
                loadAllNotes()
            }
        )
    }

    override fun filterNote(priority: String) {
        disposable.set(
            repository.filterNote(priority)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isNotEmpty()) {
                        //Log.e("Method: ","filterNote")
                        view.showAllNotes(it)
                    }else{
                        view.showEmpty()
                    }
                }
        )
    }

    override fun searchNote(title: String) {
        disposable.set(
            repository.searchNote(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isNotEmpty()) {
                        //Log.e("Method: ","searchNote")
                        view.showAllNotes(it)
                    }else{
                        view.showEmpty()
                    }
                }
        )
    }
}