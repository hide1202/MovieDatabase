package io.viewpoint.moviedatabase.ui.setting

import io.viewpoint.moviedatabase.domain.Mapper
import io.viewpoint.moviedatabase.domain.model.ConfigurationLanguage
import io.viewpoint.moviedatabase.ui.setting.model.Language
import javax.inject.Inject

class LanguageMapper @Inject constructor() : Mapper<ConfigurationLanguage, Language> {
    override suspend fun map(input: ConfigurationLanguage): Language {
        return Language(
            name = input.name,
            englishName = input.englishName,
            languageCode = input.languageCode,
        )
    }
}