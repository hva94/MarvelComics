package com.hvasoft.androidchallenge.data

import com.hvasoft.androidchallenge.data.local_db.dao.ComicDao
import com.hvasoft.androidchallenge.data.local_db.dao.CreatorDao
import com.hvasoft.androidchallenge.data.model.Comic
import com.hvasoft.androidchallenge.data.model.CreatorEntity
import com.hvasoft.androidchallenge.data.remote_db.ApiHelper
import com.hvasoft.androidchallenge.domain.ComicRepository
import com.hvasoft.androidchallenge.domain.utils.Resource
import com.hvasoft.androidchallenge.domain.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val comicDao: ComicDao,
    private val creatorDao: CreatorDao,
) : ComicRepository {

    override fun getComicsByStartingTitle(title: String): Flow<Resource<List<Comic>>> {
        return networkBoundResource(
            query = { comicDao.getComicsByStartingTitle("%$title%") },
            fetch = { apiHelper.getComicsByStartingTitle(title) },
            saveFetchResult = { comics -> comicDao.insertAll(comics.body()!!.comicsData.results) },
            shouldFetch = { comics -> comics.isEmpty() }
        )
    }

    override fun getComicById(id: String): Flow<Resource<Comic?>> {
        return networkBoundResource(
            query = { comicDao.getComicById(id) },
            fetch = { apiHelper.getComicById(id) },
            saveFetchResult = { comic -> comicDao.insertComic(comic.body()!!.comicsData.results[0]) },
            shouldFetch = { comic -> comic == null }
        )
    }

    override fun getCreatorById(id: String): Flow<Resource<CreatorEntity?>> {
        return networkBoundResource(
            query = { creatorDao.getCreatorById(id) },
            fetch = { apiHelper.getCreatorById(id) },
            saveFetchResult = { creator -> creatorDao.insertCreator(creator.body()!!.creatorsData.results[0]) },
            shouldFetch = { creator -> creator == null }
        )
    }

    override fun getAllSavedComics(): Flow<List<Comic>> {
        return comicDao.getAllComics()
    }

    override suspend fun updateFavorite(comic: Comic): Int {
        return comicDao.updateFavorite(comic)
    }

}