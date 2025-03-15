package io.viewpoint.moviedatabase.domain

import io.viewpoint.moviedatabase.api.dto.KeywordDto
import io.viewpoint.moviedatabase.model.ui.KeywordModel
import javax.inject.Inject

class KeywordModelMapper @Inject constructor() : Mapper<KeywordDto, KeywordModel> {
    override suspend fun map(input: KeywordDto): KeywordModel = KeywordModel(
        id = input.id,
        name = input.name
    )
}