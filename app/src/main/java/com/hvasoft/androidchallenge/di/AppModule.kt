package com.hvasoft.androidchallenge.di

import com.hvasoft.androidchallenge.data.repository.ComicRepositoryImpl
import com.hvasoft.androidchallenge.domain.repository.ComicRepository
import com.hvasoft.androidchallenge.domain.use_case.ComicUseCases
import com.hvasoft.androidchallenge.domain.use_case.GetComicsByStartingTitleUseCase
import com.hvasoft.androidchallenge.domain.use_case.GetComicsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideComicsRepository(repository: ComicRepositoryImpl): ComicRepository = repository

    @Singleton
    @Provides
    fun provideComicsUseCases(
        repository: ComicRepository
    ): ComicUseCases = ComicUseCases(
        getComics = GetComicsUseCase(repository),
        getComicsByStartingTitle = GetComicsByStartingTitleUseCase(repository)
    )

}