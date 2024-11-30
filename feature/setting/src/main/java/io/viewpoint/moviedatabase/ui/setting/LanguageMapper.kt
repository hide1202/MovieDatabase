package io.viewpoint.moviedatabase.ui.setting

import io.viewpoint.moviedatabase.domain.Mapper
import io.viewpoint.moviedatabase.model.api.ConfigurationLanguage
import io.viewpoint.moviedatabase.ui.setting.model.Language
import javax.inject.Inject

class LanguageMapper @Inject constructor() : Mapper<ConfigurationLanguage, Language> {
    override suspend fun map(input: ConfigurationLanguage): Language {
        return Language(
            name = input.name,
            englishName = input.english_name,
            languageCode = input.iso_639_1,
        )
    }
}