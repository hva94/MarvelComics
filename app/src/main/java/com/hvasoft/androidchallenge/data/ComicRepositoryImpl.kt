package com.hvasoft.androidchallenge.data

import com.hvasoft.androidchallenge.data.local_db.ComicDao
import com.hvasoft.androidchallenge.data.local_db.CreatorDao
import com.hvasoft.androidchallenge.data.models.Comic
import com.hvasoft.androidchallenge.data.models.ComicDataWrapper
import com.hvasoft.androidchallenge.data.models.Creator
import com.hvasoft.androidchallenge.data.models.CreatorDataWrapper
import com.hvasoft.androidchallenge.data.remote_db.ApiHelper
import com.hvasoft.androidchallenge.domain.ComicRepository
import com.hvasoft.androidchallenge.domain.utils.Resource
import com.hvasoft.androidchallenge.domain.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

class ComicRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val comicDao: ComicDao,
    private val creatorDao: CreatorDao
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

    override fun getComicById(id: String): Flow<Comic> {
        return comicDao.getComic(id)
    }

    override suspend fun getThumbnailCreatorById(id: String): Response<CreatorDataWrapper> {
        return apiHelper.getThumbnailCreatorById(id)
    }

    override fun getFavoriteComics(): Flow<Resource<List<Comic>>> {
        return comicDao.getAllComics().map { comics ->
            Resource.success(comics.filter { it.isFavorite }.sortedBy { it.title })
        }
    }

    override fun getCreatorById(id: String): Flow<Creator> {
        return creatorDao.getCreator(id)
    }

    override suspend fun fetchComicById(id: String): Response<ComicDataWrapper> {
        return apiHelper.getComicById(id)
    }

    override suspend fun updateFavorite(comic: Comic): Int {
        return comicDao.updateFavorite(comic)
    }

}