package io.viewpoint.moviedatabase.ui.search

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun MovieDetailElement(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(16.dp),
    ) {
        content()
    }
}

@Composable
internal fun MovieDetailSectionTitle(@StringRes titleResId: Int) {
    Text(
        text = stringResource(id = titleResId),
        style = LocalTextStyle.current.merge(MaterialTheme.typography.titleMedium),
        fontWeight = FontWeight.ExtraBold,
    )
}

@Composable
internal fun MovieDetailChip(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(32.dp),
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp,
            ),
        text = text,
    )
}