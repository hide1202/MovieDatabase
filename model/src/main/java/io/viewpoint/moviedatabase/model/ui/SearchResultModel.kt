package io.viewpoint.moviedatabase.model.ui

import java.io.Serializable

data class SearchResultModel(
    val id: Int,
    val title: String,
    val originalTitle: String?,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val productionCompanies: List<ProductionCompany>,
    val vote: Double,
    val releaseDate: String?
) : Serializable {
    data class ProductionCompany(
        val name: String,
        val logoUrl: String?
    )
}
