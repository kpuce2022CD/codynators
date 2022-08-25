package com.example.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.Song.Companion.PRACTICE
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
    exportSchema = false
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
                            getInstance(context).songDao().apply {
                                insert(Song(PRACTICE, "연습모드 1"))
                                insert(Song(PRACTICE, "연습모드 2"))
                                insert(Song(PRACTICE, "연습모드 3"))
                                insert(Song(PRACTICE, "연습모드 4"))
                                insert(Song(PRACTICE, "연습모드 5"))
                            }
                        }
                    }
                }
            }).build()
    }
}

@Dao
interface SongDao {

    @Query("SELECT * FROM Song")
    fun getAll(): LiveData<List<Song>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(song: Song)

    @Delete
    fun delete(song: Song)
}

@Entity
data class Song(
    val mode: Int,
    val filename: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        const val PRACTICE = 1
        const val CUSTOM = 2
    }
}