package com.example.features.main.data.repository

import com.example.features.main.data.dto.Song

interface UpLoadRepository {
    suspend fun getPractice(): List<Song>

    suspend fun upLoadSong(body: Song): Song
}