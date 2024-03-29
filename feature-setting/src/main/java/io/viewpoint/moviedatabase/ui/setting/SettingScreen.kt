package io.viewpoint.moviedatabase.ui.setting

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import io.viewpoint.moviedatabase.designsystem.Colors
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.designsystem.Palette
import io.viewpoint.moviedatabase.feature.setting.R
import io.viewpoint.moviedatabase.ui.setting.model.Language

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = viewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    SettingRoute(
        languages = uiState.languages,
        selectLanguage = uiState.selectLanguage,
        onLanguageSelected = viewModel::onLanguageSelected,
        onUseDefaultLanguageClick = viewModel::clearLanguage,
    )
}

@Composable
fun SettingRoute(
    languages: List<Language>,
    selectLanguage: Language?,
    onLanguageSelected: (Language) -> Unit,
    onUseDefaultLanguageClick: () -> Unit,
) {
    var openDropdown: Boolean by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.setting_header),
            style = LocalTextStyle.current.merge(MaterialTheme.typography.headlineSmall),
            fontWeight = FontWeight.Bold,
        )

        Row(
            modifier = Modifier
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.setting_language)
            )
            Row(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .clickable {
                        openDropdown = !openDropdown
                    },
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.setting_language),
                        color = Colors.colorPrimary,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = selectLanguage?.name ?: "",
                        style = LocalTextStyle.current.merge(MaterialTheme.typography.titleMedium),
                    )
                }
            }
            Box {
                DropdownMenu(
                    expanded = openDropdown,
                    properties = PopupProperties(
                        focusable = false,
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    ),
                    onDismissRequest = { openDropdown = false },
                ) {
                    languages.forEach { language ->
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = language.name,
                                )
                            },
                            onClick = {
                                onLanguageSelected(language)
                                openDropdown = false
                            },
                        )
                    }
                }
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = onUseDefaultLanguageClick,
        ) {
            Text(
                text = stringResource(id = R.string.setting_language_clear),
                color = Palette.white,
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingScreenPreview() {
    MovieDatabaseTheme {
        val languages = listOf(
            Language("한글", "korean", "ko"),
            Language("영어", "english", "en"),
            Language("일본어", "japanese", "ja"),
        )
        var selectLanguage by remember { mutableStateOf(0) }

        Surface {
            SettingRoute(
                languages = languages,
                selectLanguage = languages[selectLanguage],
                onLanguageSelected = { language ->
                    selectLanguage = languages.indexOfFirst {
                        it.languageCode == language.languageCode
                    }
                },
                onUseDefaultLanguageClick = {},
            )
        }
    }
}