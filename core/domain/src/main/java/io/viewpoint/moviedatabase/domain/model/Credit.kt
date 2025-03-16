package io.viewpoint.moviedatabase.domain.model

sealed class Credit(
    open val id: Int,
    open val name: String,
    open val originalName: String?,
    open val gender: Int,
    open val profilePath: String?,
)

data class Cast(
    override val id: Int,
    override val name: String,
    override val originalName: String?,
    override val gender: Int,
    override val profilePath: String?,
    val adult: Boolean,
    val castId: Int,
    val character: String?,
    val creditDd: String?,
    val knownForDepartment: String?,
    val order: Int,
    val popularity: Double
) : Credit(id, name, originalName, gender, profilePath)

data class Crew(
    override val id: Int,
    override val name: String,
    override val gender: Int,
    override val originalName: String?,
    override val profilePath: String?,
    val adult: Boolean,
    val creditId: String?,
    val department: String?,
    val job: String?,
    val knownFordepartment: String?,
    val popularity: Double
) : Credit(id, name, originalName, gender, profilePath)
