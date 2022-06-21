package com.example.codrum.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Song::class], version = 1, exportSchema = false)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {
        fun getInstance(context: Context): SongDatabase = Room
            .databaseBuilder(
                context,
                SongDatabase::class.java,
                "db"
            )
            .fallbackToDestructiveMigration()
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        getInstance(context).songDao().insert(
                            Song("연습모드", "연습 1")
                        )
                        getInstance(context).songDao().insert(
                            Song("연습모드", "연습 2")
                        )
                        getInstance(context).songDao().insert(
                            Song("연습모드", "연습 3")
                        )
                        getInstance(context).songDao().insert(
                            Song("연습모드", "연습 4")
                        )
                        getInstance(context).songDao().insert(
                            Song("연습모드", "연습 5")
                        )
                    }
                }
            }).build()
    }
}