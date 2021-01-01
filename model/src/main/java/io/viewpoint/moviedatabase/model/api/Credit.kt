package io.viewpoint.moviedatabase.model.api

sealed class Credit(
    open val id: Int,
    open val name: String,
    open val original_name: String?,
    open val gender: Int,
    open val profile_path: String?,
)

data class Cast(
    override val id: Int,
    override val name: String,
    override val original_name: String?,
    override val gender: Int,
    override val profile_path: String?,
    val adult: Boolean,
    val cast_id: Int,
    val character: String?,
    val credit_id: String?,
    val known_for_department: String?,
    val order: Int,
    val popularity: Double
) : Credit(id, name, original_name, gender, profile_path)

data class Crew(
    override val id: Int,
    override val name: String,
    override val gender: Int,
    override val original_name: String?,
    override val profile_path: String?,
    val adult: Boolean,
    val credit_id: String?,
    val department: String?,
    val job: String?,
    val known_for_department: String?,
    val popularity: Double
) : Credit(id, name, original_name, gender, profile_path)