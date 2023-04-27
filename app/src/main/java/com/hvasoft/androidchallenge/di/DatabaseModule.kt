package com.hvasoft.androidchallenge.di

import android.app.Application
import com.hvasoft.androidchallenge.data.local_db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = AppDatabase.getDatabase(application)

    @Singleton
    @Provides
    fun provideComicDao(database: AppDatabase) = database.getComicDao()

    @Singleton
    @Provides
    fun provideCreatorDao(database: AppDatabase) = database.getCreatorDao()
}