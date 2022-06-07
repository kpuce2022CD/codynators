package com.example.codrum.data


import com.example.codrum.network.IRetrofit
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val retrofitService: IRetrofit
) {
    suspend fun putSong(body: Song) = retrofitService.putSong(body)
}