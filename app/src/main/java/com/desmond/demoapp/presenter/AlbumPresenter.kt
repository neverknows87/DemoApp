package com.desmond.demoapp.presenter

import com.desmond.demoapp.model.Album
import com.desmond.demoapp.webservice.RequestFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AlbumPresenter(private  val view: AlbumView) {
    private var disposable: Disposable? = null

    private val apiService by lazy {
        RequestFactory.create()
    }

    fun getAlbumList() {
        view.showProgress()
        disposable = apiService.getAlbumList(0, 30)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    view.onSuccess(result.albums.items)
                    view.hideProgress()
                },
                { error ->
                    view.onError(error.message)
                    view.hideProgress()
                }
            )

    }

    fun onPause() {
        disposable?.dispose()
    }

    interface AlbumView {
        fun showProgress()
        fun hideProgress()
        fun onSuccess(result: List<Album>)
        fun onError(error: String?)
    }

}