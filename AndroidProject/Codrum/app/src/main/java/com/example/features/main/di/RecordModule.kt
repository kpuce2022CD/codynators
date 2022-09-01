package com.example.features.main.di

import com.example.features.recorder.MyRecorder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RecordModule {

    @Provides
    @ViewModelScoped
    fun provideMyRecorder(): MyRecorder = MyRecorder()
}