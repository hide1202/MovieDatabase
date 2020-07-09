package io.viewpoint.moviedatabase.model.common

sealed class PagingResult<TKey, TValue> {
    data class Success<TKey, TValue>(
        val data: List<TValue>,
        val previousKey: TKey?,
        val nextKey: TKey?
    ) : PagingResult<TKey, TValue>()

    data class Error<TKey, TValue>(
        val throwable: Throwable
    ) : PagingResult<TKey, TValue>()

    suspend fun <TNewValue> map(mapper: suspend (TValue) -> TNewValue): PagingResult<TKey, TNewValue> =
        when (this) {
            is Success -> {
                val newData = data.map { mapper(it) }
                Success(newData, previousKey, nextKey)
            }
            is Error -> Error(throwable)
        }
}