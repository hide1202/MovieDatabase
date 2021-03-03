package io.viewpoint.moviedatabase.domain

import androidx.paging.PagingSource
import io.viewpoint.moviedatabase.model.common.PagingResult

class LoadResultMapper<TKey : Any, TValue : Any> :
    Mapper<PagingResult<TKey, TValue>, PagingSource.LoadResult<TKey, TValue>> {
    override suspend fun map(input: PagingResult<TKey, TValue>): PagingSource.LoadResult<TKey, TValue> =
        when (input) {
            is PagingResult.Success -> PagingSource.LoadResult.Page(
                data = input.data,
                prevKey = input.previousKey,
                nextKey = input.nextKey
            )
            is PagingResult.Error -> PagingSource.LoadResult.Error(input.throwable)
        }
}