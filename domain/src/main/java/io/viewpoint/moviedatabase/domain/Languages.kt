package io.viewpoint.moviedatabase.domain

import java.util.*

object Languages {
    val SUPPORTED_LANGUAGE_CODES = listOf(Locale.ENGLISH, Locale.KOREAN, Locale.JAPANESE)

    val defaultLocale: Locale
        get() = Locale.getDefault().takeIf { default ->
            SUPPORTED_LANGUAGE_CODES.any { it.language == default.language }
        } ?: SUPPORTED_LANGUAGE_CODES[0]
}