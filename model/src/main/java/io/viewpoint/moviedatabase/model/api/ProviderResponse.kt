package io.viewpoint.moviedatabase.model.api

data class ProviderResponse(
    val id: Int?,
    val results: Map<String, ProviderInfo>?
) {
    data class ProviderInfo(
        val buy: List<Buy?>?,
        val flatrate: List<Flatrate?>?,
        val link: String?,
        val rent: List<Rent?>?
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
}