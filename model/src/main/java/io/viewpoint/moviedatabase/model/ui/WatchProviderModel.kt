package io.viewpoint.moviedatabase.model.ui

data class WatchProviderModel(
    val providers: Map<Type, List<Info>>
) {
    enum class Type {
        BUY,
        STREAMING,
        RENT
    }

    data class Info(
        val displayPriority: Int?,
        val logoUrl: String?,
        val providerId: Int?,
        val providerName: String?
    )
}