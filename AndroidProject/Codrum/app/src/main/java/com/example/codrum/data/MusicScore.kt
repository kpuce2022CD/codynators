package com.example.codrum.data

class MusicScore(Url: String) {
    var imgUrl = Url

    fun getImageUrl(): String {
        return imgUrl
    }

    fun setImageUrl(Url: String) {
        this.imgUrl = Url
    }
}