package io.viewpoint.moviedatabase.base

interface Mapper<TInput, TOutput> {
    suspend fun map(input: TInput): TOutput
}