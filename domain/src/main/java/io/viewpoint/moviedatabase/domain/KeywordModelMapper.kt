package io.viewpoint.moviedatabase.domain

import io.viewpoint.moviedatabase.model.api.Keyword
import io.viewpoint.moviedatabase.model.ui.KeywordModel
import javax.inject.Inject

class KeywordModelMapper @Inject constructor() : Mapper<Keyword, KeywordModel> {
    override suspend fun map(input: Keyword): KeywordModel = KeywordModel(
        id = input.id,
        name = input.name
    )
}