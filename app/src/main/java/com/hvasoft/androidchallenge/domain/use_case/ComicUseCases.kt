package com.hvasoft.androidchallenge.domain.use_case

data class ComicUseCases(
    val getComics: GetComicsUseCase,
    val getComicsByStartingTitle: GetComicsByStartingTitleUseCase
)