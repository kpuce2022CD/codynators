package com.example.codrum.IO

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST

interface IRetrofit {

    @POST("song")
    fun putSong(
        @Field("username") userName: String,
        @Field("filename") fileName : String
    ): Call<Song>
}