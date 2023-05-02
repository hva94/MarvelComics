package com.hvasoft.androidchallenge.domain.use_case

import android.database.sqlite.SQLiteConstraintException
import com.hvasoft.androidchallenge.core.exceptions.ComicsException
import com.hvasoft.androidchallenge.core.exceptions.TypeError
import com.hvasoft.androidchallenge.data.model.Comic
import com.hvasoft.androidchallenge.domain.ComicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateFavoriteUseCase(
    private val repository: ComicRepository,
) {

    suspend operator fun invoke(comic: Comic) = withContext(Dispatchers.IO) {
        try {
            val result = repository.updateFavorite(comic)
            if (result == 0) throw ComicsException(TypeError.UPDATE)
        } catch (e: SQLiteConstraintException){
            throw ComicsException(TypeError.UPDATE)
        }
    }

}