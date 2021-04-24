package io.viewpoint.moviedatabase.domain.preferences

interface PreferencesService {
    suspend fun <T : Any> getValue(key: PreferenceKey<T>, defaultValue: T? = null): T?

    suspend fun <T : Any> putValue(key: PreferenceKey<T>, value: T?)

    suspend fun <T : Any> getValue(key: PreferenceKey<T>, converter: (String) -> T): T?

    suspend fun <T : Any> putValue(key: PreferenceKey<T>, value: T?, converter: (T) -> String)

    suspend fun <T : Any> getValues(
        key: PreferenceKey<T>,
        converter: (String) -> T
    ): List<T>

    suspend fun <T : Any> putValues(
        key: PreferenceKey<T>,
        value: List<T>,
        converter: (T) -> String
    )

    suspend fun <T : Any> addValue(
        key: PreferenceKey<T>,
        value: T,
        deserializer: (String) -> T,
        serializer: (T) -> String
    ) {
        val previous = getValues(key, deserializer)
        val new = previous.plus(value)
        putValues(key, new, serializer)
    }

    suspend fun <T : Any> removeValue(
        key: PreferenceKey<T>,
        value: T,
        deserializer: (String) -> T,
        serializer: (T) -> String
    ) {
        val previous = getValues(key, deserializer)
        val new = previous.minus(value)
        putValues(key, new, serializer)
    }
}

// string specialization
suspend fun PreferencesService.getValues(
    key: PreferenceKey<String>
): List<String> = getValues(key) {
    it
}

suspend fun PreferencesService.putValues(
    key: PreferenceKey<String>,
    value: List<String>
) = putValues(key, value) {
    it
}

suspend fun PreferencesService.addValue(
    key: PreferenceKey<String>,
    value: String
) = addValue(key, value, { it }, { it })

suspend fun PreferencesService.removeValue(
    key: PreferenceKey<String>,
    value: String
) = removeValue(key, value, { it }, { it })