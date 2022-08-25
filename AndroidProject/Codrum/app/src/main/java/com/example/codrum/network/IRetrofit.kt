package com.example.codrum.network

import com.example.data.Song
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IRetrofit {
    @POST("/")
    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    suspend fun putSong(
        @Body jsonparams: Song
    ): Song
}