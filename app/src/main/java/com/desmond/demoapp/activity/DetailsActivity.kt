package com.desmond.demoapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.desmond.demoapp.R
import com.desmond.demoapp.model.Album
import com.desmond.demoapp.util.Util
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {
    lateinit var album: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        album = intent.getSerializableExtra("album-extra") as Album
        val coverImage = findViewById<ImageView>(R.id.squaredImageView)
        val albumName = findViewById<TextView>(R.id.album_name)
        val artistName = findViewById<TextView>(R.id.artist_name)
        val songCount = findViewById<TextView>(R.id.song_counts)
        val releaseDate = findViewById<TextView>(R.id.release_date)


        Picasso.get().load(album.images[0].url).into(coverImage)
        albumName.text = album.name
        artistName.text = Util.getNamesInLine(album.artists)
        val totalTracks = album.total_tracks
        if (totalTracks < 2) {
            songCount.text = getString(R.string.num_song, totalTracks)
        } else {
            songCount.text = getString(R.string.num_songs, totalTracks)
        }
        releaseDate.text = getString(R.string.release_date, album.release_date)
    }
}