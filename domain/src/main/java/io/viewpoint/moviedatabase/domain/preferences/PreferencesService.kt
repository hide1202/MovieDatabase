package io.viewpoint.moviedatabase.domain.preferences

interface PreferencesService {
    fun <T : Any> getValue(key: PreferenceKey<T>, defaultValue: T? = null): T?

    fun <T : Any> putValue(key: PreferenceKey<T>, value: T?)

    fun <T : Any> getValue(key: PreferenceKey<T>, converter: (String) -> T): T?

    fun <T : Any> putValue(key: PreferenceKey<T>, value: T?, converter: (T) -> String)

    fun <T : Any> getValues(
        key: PreferenceKey<T>,
        converter: (String) -> T
    ): List<T>

    fun <T : Any> putValues(
        key: PreferenceKey<T>,
        value: List<T>,
        converter: (T) -> String
    )

    fun <T : Any> addValue(
        key: PreferenceKey<T>,
        value: T,
        deserializer: (String) -> T,
        serializer: (T) -> String
    ) {
        val previous = getValues(key, deserializer)
        val new = previous.plus(value)
        putValues(key, new, serializer)
    }

    fun <T : Any> removeValue(
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
fun PreferencesService.getValues(
    key: PreferenceKey<String>
): List<String> = getValues(key) { it }

fun PreferencesService.putValues(
    key: PreferenceKey<String>,
    value: List<String>
) = putValues(key, value) { it }

fun PreferencesService.addValue(
    key: PreferenceKey<String>,
    value: String
) = addValue(key, value, { it }, { it })

fun PreferencesService.removeValue(
    key: PreferenceKey<String>,
    value: String
) = removeValue(key, value, { it }, { it })