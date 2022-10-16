package com.example.features.main.data.repository

import com.example.data.SongDao
import com.example.features.main.data.dto.Song
import com.example.features.main.data.dto.toUploadFile
import com.example.features.main.data.service.UploadService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpLoadRepositoryImpl @Inject constructor(
    private val upLoadService: UploadService,
    private val songDatabaseDao: SongDao
) : UpLoadRepository {
    override suspend fun getPractice(): List<Song> =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            songDatabaseDao.getAll()
        }

    override suspend fun upLoadSong(body: Song) {
        upLoadService.putSong(body.toUploadFile())
    }

}