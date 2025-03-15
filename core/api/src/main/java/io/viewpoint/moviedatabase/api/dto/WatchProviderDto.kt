package io.viewpoint.moviedatabase.api.dto

data class WatchProviderDto(
    val link: String,
    val buy: List<BuyDto> = emptyList(),
    val flatrate: List<FlatrateDto> = emptyList(),
    val rent: List<RentDto> = emptyList()
) {
    data class BuyDto(
        val display_priority: Int?,
        val logo_path: String?,
        val provider_id: Int?,
        val provider_name: String?
    )

    data class FlatrateDto(
        val display_priority: Int?,
        val logo_path: String?,
        val provider_id: Int?,
        val provider_name: String?
    )

    data class RentDto(
        val display_priority: Int?,
        val logo_path: String?,
        val provider_id: Int?,
        val provider_name: String?
    )
}