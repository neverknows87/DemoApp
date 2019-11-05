package com.desmond.demoapp.model

class Response {
    data class Result(val albums: Data)
    data class Data(val items: List<Album>)
}