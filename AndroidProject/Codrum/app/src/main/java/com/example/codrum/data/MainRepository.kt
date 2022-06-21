package com.example.codrum.data


import androidx.lifecycle.LiveData
import com.example.codrum.network.IRetrofit
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val retrofitService: IRetrofit,
    private var songDatabase: SongDatabase
) {
    suspend fun putSong(body: Song) = retrofitService.putSong(body)

    private val dao = songDatabase.songDao()

    var allSong: LiveData<List<Song>> = dao.getAll()

    fun insert(song: Song) {
        dao.insert(song)
    }

    fun delete(song: Song) {
        dao.delete(song)
    }

}