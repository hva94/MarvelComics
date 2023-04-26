package com.hvasoft.androidchallenge.domain.use_case

data class ComicsUseCases(
    val getComics: GetComicsUseCase,
    val getComicsByStartingTitle: GetComicsByStartingTitleUseCase,
    val getComicDetail: GetComicDetailUseCase,
    val getVariants: GetVariantsUseCase,
    val updateFavorite: UpdateFavoriteUseCase,
    val getFavoriteComics: GetFavoriteComicsUseCase
)