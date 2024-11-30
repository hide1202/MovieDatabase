package io.viewpoint.moviedatabase.model.api

data class CreditsResponse(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
)