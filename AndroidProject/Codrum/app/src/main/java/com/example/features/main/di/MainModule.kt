package com.example.features.main.di

import com.example.features.main.data.repository.FirebaseRepository
import com.example.features.main.data.repository.FirebaseRepositoryImpl
import com.example.features.main.data.repository.UpLoadRepository
import com.example.features.main.data.repository.UpLoadRepositoryImpl
import com.example.features.main.data.service.UploadService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {
    @Binds
    @Singleton
    abstract fun bindFirebaseRepository(
        repository: FirebaseRepositoryImpl
    ): FirebaseRepository

    @Binds
    @Singleton
    abstract fun bindUpLoadRepository(
        repository: UpLoadRepositoryImpl
    ): UpLoadRepository

    companion object{
        @Provides
        @Singleton
        fun provideUploadService(retrofit: Retrofit): UploadService{
            return retrofit.create(UploadService::class.java)
        }
    }
}