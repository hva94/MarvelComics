package com.hvasoft.androidchallenge.data.repository

import com.hvasoft.androidchallenge.data.local_db.dao.ComicDao
import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.data.remote_db.ApiHelper
import com.hvasoft.androidchallenge.domain.repository.ComicRepository
import com.hvasoft.androidchallenge.domain.utils.Resource
import com.hvasoft.androidchallenge.domain.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val comicDao: ComicDao,
) : ComicRepository {

    override fun getComics(): Flow<Resource<List<Comic>>> {
        return networkBoundResource(
            query = { comicDao.getAllComics() },
            fetch = { apiHelper.getComics() },
            saveFetchResult = { comics -> comicDao.insertAll(comics.body()!!.data.results) }
        )
    }

    override fun getComicsByStartingTitle(title: String): Flow<Resource<List<Comic>>> {
        return networkBoundResource(
            query = { comicDao.getComicsByStartingTitle("%$title%") },
            fetch = { apiHelper.getComicsByStartingTitle(title) },
            saveFetchResult = { comics -> comicDao.insertAll(comics.body()!!.data.results) }
        )
    }

    override fun getComicDetail(comicId: String): Flow<Comic> {
        return comicDao.getComic(comicId)
    }

}