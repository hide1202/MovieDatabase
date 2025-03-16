package io.viewpoint.moviedatabase.domain.model

data class WatchProvider(
    val link: String,
    val buy: List<Buy> = emptyList(),
    val flatrate: List<Flatrate> = emptyList(),
    val rent: List<Rent> = emptyList()
) {
    data class Buy(
        val displayPriority: Int?,
        val logoPath: String?,
        val providerId: Int?,
        val providerName: String?
    )

    data class Flatrate(
        val displayPriority: Int?,
        val logoPath: String?,
        val providerId: Int?,
        val providerName: String?
    )

    data class Rent(
        val displayPriority: Int?,
        val logoPath: String?,
        val providerId: Int?,
        val providerName: String?
    )
}