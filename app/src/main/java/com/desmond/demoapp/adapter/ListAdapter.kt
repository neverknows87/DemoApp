package com.desmond.demoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desmond.demoapp.R
import com.desmond.demoapp.model.Album
import com.squareup.picasso.Picasso

class ListAdapter(ctx: Context, data: List<Album>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var dataList : List<Album> = data
    private val inflater: LayoutInflater = LayoutInflater.from(ctx)

    fun updateData(data: List<Album>) {
        dataList = data
        notifyDataSetChanged()
    }

    fun getData(): List<Album> {
        return dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.album_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = dataList[position]

        Picasso.get().load(album.images?.get(1)?.url).into(holder.albumCover)
        holder.artistName.text = album.artists?.get(0)?.name
        holder.albumName.text = album.name
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var albumCover: ImageView = itemView.findViewById(R.id.iv_cover) as ImageView
        var artistName: TextView = itemView.findViewById(R.id.tv_artist) as TextView
        var albumName: TextView = itemView.findViewById(R.id.tv_album) as TextView
    }
}