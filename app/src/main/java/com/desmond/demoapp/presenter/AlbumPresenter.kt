package com.desmond.demoapp.presenter

import com.desmond.demoapp.*
import com.desmond.demoapp.model.Album
import com.desmond.demoapp.webservice.RequestFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AlbumPresenter(private  val view: AlbumView) {
    private var disposable: Disposable? = null

    private val apiService by lazy {
        RequestFactory.createApiService()
    }

    private val authService by lazy {
        RequestFactory.createAuthService()
    }

    fun getToken() {
        view.showProgress()
        disposable = authService.authorize(CLIENT_ID, CLIENT_SECRET, GRANT_TYPE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { token ->
                    ACCESSTOKEN = token.access_token
                    getAlbumList()
                    view.hideProgress()
                },
                { error ->
                    when(error) {
                        is UnknownHostException -> view.onError("No Network")
                        is SocketTimeoutException -> view.onError("Network Timeout")
                        else -> view.onError(error.message)
                    }
                    view.hideProgress()
                }
            )
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
                    when(error) {
                        is UnknownHostException -> view.onError("No Network")
                        is SocketTimeoutException -> view.onError("Network Timeout")
                        else -> view.onError(error.message)
                    }
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