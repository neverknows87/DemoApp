package com.desmond.demoapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.desmond.demoapp.R
import com.desmond.demoapp.adapter.ListAdapter
import com.desmond.demoapp.model.Album
import com.desmond.demoapp.presenter.AlbumPresenter
import com.desmond.demoapp.view.GridSpacingItemDecoration

class AlbumActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, AlbumPresenter.AlbumView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var adapter: ListAdapter? = null
    private var presenter: AlbumPresenter? = null
    private var arrayList: List<Album> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        recyclerView = findViewById(R.id.recyclerview)
        swipeRefresh = findViewById(R.id.swipeRefreshLayout)

        //Add listener for swipe to refresh
        swipeRefresh.setOnRefreshListener(this)

        //Initialize views and adapter
        adapter = ListAdapter(this, arrayList)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GridSpacingItemDecoration(3, 20, false))
        recyclerView.layoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)

        //Initialize presenter
        presenter = AlbumPresenter(this)
        //Call API
        presenter?.getAlbumList()
    }

    override fun onRefresh() {
        presenter?.getAlbumList()
    }

    override fun showProgress() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        swipeRefresh.isRefreshing = false
    }

    override fun onSuccess(result: List<Album>) {
        adapter?.updateData(result)
    }

    override fun onError(error: String?) {
        Log.e("Main", error)
    }

}
