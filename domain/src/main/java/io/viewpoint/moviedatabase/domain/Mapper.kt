package io.viewpoint.moviedatabase.domain

interface Mapper<TInput, TOutput> {
    suspend fun map(input: TInput): TOutput
}