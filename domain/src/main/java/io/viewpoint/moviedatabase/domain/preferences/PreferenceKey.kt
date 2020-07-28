package io.viewpoint.moviedatabase.domain.preferences

import kotlin.reflect.KClass

class PreferenceKey<T : Any>(
    val key: String,
    val type: KClass<T>
)
