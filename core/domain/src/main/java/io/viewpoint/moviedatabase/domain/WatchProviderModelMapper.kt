package io.viewpoint.moviedatabase.domain

import io.viewpoint.moviedatabase.domain.model.WatchProvider
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
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
                        displayPriority = it.displayPriority,
                        logoUrl = it.logoPath?.let { profilePath ->
                            configurationRepository.getImageUrl {
                                profilePath
                            }
                        },
                        providerId = it.providerId,
                        providerName = it.providerName,
                    )
                },
            WatchProviderModel.Type.STREAMING to streaming.filterNotNull()
                .parallelMap {
                    WatchProviderModel.Info(
                        displayPriority = it.displayPriority,
                        logoUrl = it.logoPath?.let { profilePath ->
                            configurationRepository.getImageUrl {
                                profilePath
                            }
                        },
                        providerId = it.providerId,
                        providerName = it.providerName,
                    )
                },
            WatchProviderModel.Type.RENT to rent.filterNotNull()
                .parallelMap {
                    WatchProviderModel.Info(
                        displayPriority = it.displayPriority,
                        logoUrl = it.logoPath?.let { profilePath ->
                            configurationRepository.getImageUrl {
                                profilePath
                            }
                        },
                        providerId = it.providerId,
                        providerName = it.providerName,
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