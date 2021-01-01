package io.viewpoint.moviedatabase.domain

import io.viewpoint.moviedatabase.domain.repository.ConfigurationRepository
import io.viewpoint.moviedatabase.model.api.Credit
import io.viewpoint.moviedatabase.model.ui.CreditModel
import javax.inject.Inject

// TODO Arrange package
class CreditModelMapper @Inject constructor(
    private val configurationRepository: ConfigurationRepository
) : Mapper<Credit, CreditModel> {
    override suspend fun map(input: Credit): CreditModel = CreditModel(
        id = input.id,
        name = input.name,
        profileUrl = input.profile_path?.let { profilePath ->
            configurationRepository.getImageUrl {
                profilePath
            }
        }
    )
}