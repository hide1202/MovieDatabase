package io.viewpoint.moviedatabase.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle

private val LightColorScheme = lightColorScheme(
    primary = Colors.colorPrimary,
    onPrimary = Colors.defaultForeground,
    surface = Colors.defaultBackground,
)
private val DarkColorScheme = darkColorScheme(
    primary = Colors.colorPrimaryDark,
    onPrimary = Colors.defaultBackground,
    surface = Colors.defaultForeground,
)

@Composable
fun MovieDatabaseTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (!useDarkTheme) {
            LightColorScheme
        } else {
            DarkColorScheme
        }
    CompositionLocalProvider(
        LocalTextStyle provides TextStyle(color = colorScheme.onPrimary)
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = {
                Surface {
                    content()
                }
            }
        )
    }
}