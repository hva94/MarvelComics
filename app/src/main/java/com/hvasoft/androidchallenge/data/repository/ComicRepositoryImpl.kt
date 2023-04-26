package com.hvasoft.androidchallenge.data.repository

import com.hvasoft.androidchallenge.data.local_db.dao.ComicDao
import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.data.models.ComicDataWrapper
import com.hvasoft.androidchallenge.data.models.CreatorDataWrapper
import com.hvasoft.androidchallenge.data.remote_db.ApiHelper
import com.hvasoft.androidchallenge.domain.repository.ComicRepository
import com.hvasoft.androidchallenge.domain.utils.Resource
import com.hvasoft.androidchallenge.domain.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val comicDao: ComicDao,
) : ComicRepository {

    override fun getComics(): Flow<Resource<List<Comic>>> {
        return comicDao.getAllComics().map { comics ->
            Resource.success(comics.sortedBy { it.title })
        }
    }

    override fun getComicsByStartingTitle(title: String): Flow<Resource<List<Comic>>> {
        return networkBoundResource(
            query = { comicDao.getComicsByStartingTitle("%$title%") },
            fetch = { apiHelper.getComicsByStartingTitle(title) },
            saveFetchResult = { comics -> comicDao.insertAll(comics.body()!!.comicsData.results) }
        )
    }

    override fun getComicDetail(comicId: String): Flow<Comic> {
        return comicDao.getComic(comicId)
    }

    override suspend fun getVariant(comicId: String): Response<ComicDataWrapper> {
        return apiHelper.getVariant(comicId)
    }

    override suspend fun updateFavorite(comic: Comic): Int {
        return comicDao.updateFavorite(comic)
    }

    override suspend fun getThumbnailCreator(creatorId: String): Response<CreatorDataWrapper> {
        return apiHelper.getThumbnailCreator(creatorId)
    }

    override fun getFavoriteComics(): Flow<Resource<List<Comic>>> {
        return comicDao.getAllComics().map { comics ->
            Resource.success(comics.filter { it.isFavorite }.sortedBy { it.title })
        }
    }

}