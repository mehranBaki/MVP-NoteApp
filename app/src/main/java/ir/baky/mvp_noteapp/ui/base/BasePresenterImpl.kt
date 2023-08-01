package ir.baky.mvp_noteapp.ui.base


import io.reactivex.rxjava3.disposables.SerialDisposable

open class BasePresenterImpl : BasePresenter{

    //@NonNull
    //var disposable: Disposable? = null
    //var disposable by lazy { CompositeDisposable() }
    val disposable by lazy { SerialDisposable() }

    override fun onStop() {
        disposable?.let {
            it.dispose()
        }
    }
}