package com.desmond.demoapp.util

import com.desmond.demoapp.model.ArtistList

class Util {
    companion object {
        fun getNamesInLine(list: List<ArtistList>) : String {
            val sb = StringBuilder()
            for (index in list.indices) {
                sb.append(list[index].name)
                if (index < list.size - 1) {
                    sb.append(", ")
                }
            }
            return sb.toString()
        }
    }
}