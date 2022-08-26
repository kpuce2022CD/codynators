package com.example.features.main.data.repository

import com.example.data.SongDao
import com.example.features.main.data.dto.Song
import com.example.features.main.data.service.UploadService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpLoadRepositoryImpl @Inject constructor(
    private val upLoadService: UploadService,
    private val songDatabaseDao: SongDao
) {
    suspend fun getPractice(): List<Song> =
        CoroutineScope(Dispatchers.IO).async {
            songDatabaseDao.getAll()
        }.await()

    suspend fun upLoadSong(body: Song) = upLoadService.putSong(body)

    suspend fun insertSong(song: Song) {
        CoroutineScope(Dispatchers.IO).launch {
            songDatabaseDao.insert(song)
        }
    }

    suspend fun delete(song: Song) {
        CoroutineScope(Dispatchers.IO).launch {
            songDatabaseDao.delete(song)
        }
    }
}