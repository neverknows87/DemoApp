package com.desmond.demoapp.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.desmond.demoapp.ACCESSTOKEN
import com.desmond.demoapp.R
import com.desmond.demoapp.adapter.ListAdapter
import com.desmond.demoapp.model.Album
import com.desmond.demoapp.presenter.AlbumPresenter
import com.desmond.demoapp.view.AlbumViewModel
import com.desmond.demoapp.view.GridSpacingItemDecoration

class AlbumActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    AlbumPresenter.AlbumView, ListAdapter.ItemClickedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var adapter: ListAdapter? = null
    private var presenter: AlbumPresenter? = null
    private var arrayList: List<Album> = ArrayList()
    private lateinit var albumViewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        // Get a new or existing AlbumViewModel from the ViewModelProvider.
        albumViewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)

        // Add an observer on the LiveData
        albumViewModel.allAlbums.observe(this, Observer { albums ->
            // Update the cached data in the adapter.
            albums?.let {
                adapter?.updateData(it)
            }
        })

        recyclerView = findViewById(R.id.recyclerview)
        swipeRefresh = findViewById(R.id.swipeRefreshLayout)

        //Add listener for swipe to refresh
        swipeRefresh.setOnRefreshListener(this)

        //Initialize views and adapter
        adapter = ListAdapter(this, arrayList, this)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GridSpacingItemDecoration(3, 20, false))
        recyclerView.layoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)

        //Initialize presenter
        presenter = AlbumPresenter(this)
        //Call API
        presenter?.getToken()
    }

    override fun onPause() {
        super.onPause()
        presenter?.onPause()
    }

    override fun onRefresh() {
        if (TextUtils.isEmpty(ACCESSTOKEN))
            presenter?.getToken()
        else
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
        albumViewModel.insert(result)
    }

    override fun onError(error: String?) {
        showErrorDialog(error)
    }

    override fun onItemClicked(view: View, album: Album) {
        detailsPreview(view, album)
    }

    fun showErrorDialog(errorMessage: String?) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setNegativeButton("OK", null)
            .create()
        alertDialog.show()

    }

    //Perform shared-element transition by material design
    fun detailsPreview(view: View, album: Album) {
        val coverImage = view.findViewById(R.id.iv_cover) as ImageView
        val pairImageView = Pair.create<View, String>(coverImage, "image_cover")
        val pairs = ArrayList<Pair<View,String>>()
        pairs.add(pairImageView)
        val pairArray: Array<Pair<View, String>> = pairs.toTypedArray()
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this@AlbumActivity,
            *pairArray
        )
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("album-extra", album)
        startActivity(intent, options.toBundle())
    }
}
