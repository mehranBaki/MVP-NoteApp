package ir.baky.mvp_noteapp.ui.base

import io.reactivex.rxjava3.disposables.Disposable

open class BasePresenterImpl : BasePresenter{

    //@NonNull
    var disposable: Disposable? = null

    override fun onStop() {
        disposable?.let {
            it.dispose()
        }
    }
}