package com.example.features.main.data.service

import com.example.features.main.data.dto.File
import com.example.features.main.data.dto.Song
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UploadService {
    @POST("/")
    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    suspend fun putSong(
        @Body jsonparams: File
    ): Song
}