package io.viewpoint.moviedatabase.api.dto

data class WatchProviderResponse(
    val id: Int?,
    val results: Map<String, WatchProviderDto>
)