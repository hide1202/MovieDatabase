package io.viewpoint.moviedatabase.repository

import arrow.core.Option

interface ConfigurationRepository {
    suspend fun getImageBaseUrl(): Option<String>
}