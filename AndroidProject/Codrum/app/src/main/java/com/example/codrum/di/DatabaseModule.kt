package com.example.codrum.di

import android.content.Context
import com.example.codrum.data.SongDatabase
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
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): SongDatabase = SongDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideSongDao(appDatabase: SongDatabase) = appDatabase.songDao()

}