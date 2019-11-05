package com.desmond.demoapp.model

class Album {
    var type: String? = null
    var release_date: String? = null
    var name: String? = null
    var id: String? = null
    var album_type: String? = null
    var total_tracks: Int = 0
    var images: List<Cover>? = null
    var artists: List<Artist>? = null
}