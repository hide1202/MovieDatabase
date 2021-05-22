package io.viewpoint.moviedatabase.model.api

data class WatchProviderResponse(
    val id: Int?,
    val results: Map<String, WatchProvider>
)