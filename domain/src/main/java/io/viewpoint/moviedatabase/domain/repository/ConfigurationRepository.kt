package io.viewpoint.moviedatabase.domain.repository

import arrow.core.Option

interface ConfigurationRepository {
    suspend fun getImageBaseUrl(): Option<String>
}