package com.example.codrum.IO

import com.example.codrum.Model.Song
import retrofit2.Call
import retrofit2.http.*

interface IRetrofit {
    @POST("/")
    @Headers("accept: application/json",
        "content-type: application/json")
    fun putSong(
        @Body jsonparams: Song
    ): Call<Song>
}