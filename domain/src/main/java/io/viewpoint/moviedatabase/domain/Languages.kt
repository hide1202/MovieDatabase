package io.viewpoint.moviedatabase.domain

import java.util.*

object Languages {
    val SUPPORTED_LANGUAGE_CODES = listOf(Locale.ENGLISH, Locale.KOREAN, Locale.JAPANESE)

    var defaultLocale: Locale = Locale.getDefault().takeIf { default ->
        SUPPORTED_LANGUAGE_CODES.any { it.language == default.language }
    } ?: SUPPORTED_LANGUAGE_CODES[0]
        set(value) {
            field = value.takeIf {
                SUPPORTED_LANGUAGE_CODES.contains(value)
            } ?: SUPPORTED_LANGUAGE_CODES[0]
        }
}