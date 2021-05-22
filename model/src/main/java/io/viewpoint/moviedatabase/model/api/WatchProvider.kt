package io.viewpoint.moviedatabase.model.api

data class WatchProvider(
    val link: String,
    val buy: List<Buy> = emptyList(),
    val flatrate: List<Flatrate> = emptyList(),
    val rent: List<Rent> = emptyList()
) {
    data class Buy(
        val display_priority: Int?,
        val logo_path: String?,
        val provider_id: Int?,
        val provider_name: String?
    )

    data class Flatrate(
        val display_priority: Int?,
        val logo_path: String?,
        val provider_id: Int?,
        val provider_name: String?
    )

    data class Rent(
        val display_priority: Int?,
        val logo_path: String?,
        val provider_id: Int?,
        val provider_name: String?
    )
}