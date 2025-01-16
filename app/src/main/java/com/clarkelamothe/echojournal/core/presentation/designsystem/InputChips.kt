package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.HashtagIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Gray6

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean = true,
    trailingIcon: @Composable () -> Unit = {},
    onClick: () -> Unit = {}
) {
    InputChip(
        onClick = onClick,
        label = {
            Text(
                maxLines = 1,
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        },
        selected = selected,
        avatar = {
            Icon(
                imageVector = HashtagIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outlineVariant,
            )
        },
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(16.dp),
        colors = InputChipDefaults.inputChipColors(
            containerColor = Gray6,
            selectedContainerColor = Gray6,
            leadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            trailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = 0.3f
            )
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun ChipPreview() {
    Chip(text = "Work", selected = true)
}