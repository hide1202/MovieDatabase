package io.viewpoint.moviedatabase.domain.preferences

import kotlin.reflect.KClass

open class PreferenceKey<T : Any>(
    val key: String,
    val type: KClass<T>
)

class PreferenceKeyWithDefault<T : Any>(
    key: String,
    type: KClass<T>,
    val defaultProvider: () -> T
) : PreferenceKey<T>(key, type)
