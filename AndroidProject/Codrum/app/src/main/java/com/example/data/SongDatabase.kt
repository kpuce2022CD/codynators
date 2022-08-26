package com.example.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.features.main.data.dto.Song
import com.example.features.main.data.dto.Song.Companion.PRACTICE
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

// 추가된 곡 DB
@Database(
    entities = [Song::class],
    version = 1,
    exportSchema = false
)
abstract class CustomSongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {
        fun getInstance(context: Context): CustomSongDatabase = Room
            .databaseBuilder(context, CustomSongDatabase::class.java, "custom.db")
            .build()
    }
}

// 연습모드 곡 DB
@Database(
    entities = [Song::class],
    version = 1,
    exportSchema = false,
)
abstract class PracticeSongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {
        fun getInstance(context: Context): PracticeSongDatabase = Room
            .databaseBuilder(context, PracticeSongDatabase::class.java, "practice.db")
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // 기본 곡 추가
                    Executors.newSingleThreadExecutor().execute {
                        runBlocking {
                            getInstance(context).songDao().insertAll(
                                Song(PRACTICE, "연습모드 1"),
                                Song(PRACTICE, "연습모드 2"),
                                Song(PRACTICE, "연습모드 3"),
                                Song(PRACTICE, "연습모드 4"),
                                Song(PRACTICE, "연습모드 5"),
                            )
                        }
                    }
                }
            }).build()
    }
}

@Dao
interface SongDao {

    @Query("SELECT * FROM Song")
    suspend fun getAll(): List<Song>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(song: Song)

    // DB 초기화용
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg song: Song)

    @Delete
    suspend fun delete(song: Song)
}
