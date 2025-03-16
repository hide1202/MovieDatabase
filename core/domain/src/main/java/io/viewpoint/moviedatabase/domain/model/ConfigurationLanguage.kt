package io.viewpoint.moviedatabase.domain.model

/**
 * Represents a language that is supported by the API.
 *
 * @property name The name of the language.
 * @property englishName The English name of the language.
 * @property languageCode The language code (The ISO 639-1 code)
 */
data class ConfigurationLanguage(
    val name: String,
    val englishName: String,
    val languageCode: String,
)
