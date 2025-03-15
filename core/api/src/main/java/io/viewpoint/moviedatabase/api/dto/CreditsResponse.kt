package io.viewpoint.moviedatabase.api.dto

data class CreditsResponse(
    val id: Int,
    val cast: List<CastDto>,
    val crew: List<CrewDto>
)