package com.hvasoft.androidchallenge.di

import com.hvasoft.androidchallenge.data.ComicRepositoryImpl
import com.hvasoft.androidchallenge.domain.ComicRepository
import com.hvasoft.androidchallenge.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideComicsRepository(repository: ComicRepositoryImpl): ComicRepository = repository

    @Singleton
    @Provides
    fun provideComicsUseCases(
        repository: ComicRepository
    ): ComicsUseCases = ComicsUseCases(
        getComics = GetComicsUseCase(repository),
        getComicsByStartingTitle = GetComicsByStartingTitleUseCase(repository),
        getComicDetail = GetComicDetailUseCase(repository),
        updateFavorite = UpdateFavoriteUseCase(repository),
        getFavoriteComics = GetFavoriteComicsUseCase(repository),
        getFirstCreator = GetFirstCreatorUseCase(repository),
        getVariants = GetVariantsUseCase(repository)
    )

}