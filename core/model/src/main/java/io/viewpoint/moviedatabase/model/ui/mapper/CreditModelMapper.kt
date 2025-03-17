package io.viewpoint.moviedatabase.model.ui.mapper

import io.viewpoint.moviedatabase.domain.Mapper
import io.viewpoint.moviedatabase.domain.model.Credit
import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.model.ui.CreditModel
import javax.inject.Inject

// TODO Arrange package
class CreditModelMapper @Inject constructor(
    private val configurationRepository: ConfigurationRepository
) : Mapper<Credit, CreditModel> {
    override suspend fun map(input: Credit): CreditModel = CreditModel(
        id = input.id,
        name = input.name,
        profileUrl = input.profilePath?.let { profilePath ->
            configurationRepository.getImageUrl {
                profilePath
            }
        }
    )
}