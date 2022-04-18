package com.example.codrum.IO

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface IRetrofit {
    @POST("song")
    fun putSong(
        @Query("username") userName: String,
        @Query("filename") fileName : String
    ): Call<Song>

    @GET("song")
    fun getSong(
        @Query("username") userName: String,
        @Query("filename") fileName : String
    ): Call<Song>
}