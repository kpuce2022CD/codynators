package com.example.di

import android.content.Context
import com.example.data.PracticeSongDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePracticeSongDatabase(
        @ApplicationContext context: Context
    ): PracticeSongDatabase = PracticeSongDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideSongDao(appDatabase: PracticeSongDatabase) = appDatabase.songDao()

}