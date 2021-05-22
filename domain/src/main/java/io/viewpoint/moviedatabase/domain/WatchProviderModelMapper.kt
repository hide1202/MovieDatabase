package io.viewpoint.moviedatabase.domain

import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.model.api.WatchProvider
import io.viewpoint.moviedatabase.model.ui.WatchProviderModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class WatchProviderModelMapper @Inject constructor(
    private val configurationRepository: ConfigurationRepository
) : Mapper<WatchProvider, WatchProviderModel> {
    override suspend fun map(input: WatchProvider): WatchProviderModel = WatchProviderModel(
        providers = createWatchProviderMap(input)
    )

    private suspend fun createWatchProviderMap(
        input: WatchProvider
    ): Map<WatchProviderModel.Type, List<WatchProviderModel.Info>> {
        val buy = input.buy
        val streaming = input.flatrate
        val rent = input.rent

        return mapOf(
            WatchProviderModel.Type.BUY to buy.filterNotNull()
                .parallelMap {
                    WatchProviderModel.Info(
                        displayPriority = it.display_priority,
                        logoUrl = it.logo_path?.let { profilePath ->
                            configurationRepository.getImageUrl {
                                profilePath
                            }
                        },
                        providerId = it.provider_id,
                        providerName = it.provider_name,
                    )
                },
            WatchProviderModel.Type.STREAMING to streaming.filterNotNull()
                .parallelMap {
                    WatchProviderModel.Info(
                        displayPriority = it.display_priority,
                        logoUrl = it.logo_path?.let { profilePath ->
                            configurationRepository.getImageUrl {
                                profilePath
                            }
                        },
                        providerId = it.provider_id,
                        providerName = it.provider_name,
                    )
                },
            WatchProviderModel.Type.RENT to rent.filterNotNull()
                .parallelMap {
                    WatchProviderModel.Info(
                        displayPriority = it.display_priority,
                        logoUrl = it.logo_path?.let { profilePath ->
                            configurationRepository.getImageUrl {
                                profilePath
                            }
                        },
                        providerId = it.provider_id,
                        providerName = it.provider_name,
                    )
                },
        )
    }

    private suspend inline fun <T, R> List<T>.parallelMap(crossinline transform: suspend (T) -> R): List<R> =
        coroutineScope {
            val list = map {
                async { transform(it) }
            }
            list.awaitAll()
        }
}