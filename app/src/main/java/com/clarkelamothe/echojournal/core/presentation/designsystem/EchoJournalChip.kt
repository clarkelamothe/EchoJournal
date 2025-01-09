package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CloseIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.StressedIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme

@Composable
fun EchoJournalChip(
    selected: Boolean = false,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    avatar: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit
) {
    InputChip(
        selected = selected,
        onClick = onClick,
        label = label,
        avatar = avatar,
        border = BorderStroke(
            width = 1.dp,
            color = if (selected)
                MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.outlineVariant
        ),
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(16.dp),
        colors = InputChipDefaults.inputChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = Color.Transparent,
            selectedLeadingIconColor = Color.Unspecified,
            selectedTrailingIconColor = Color.Unspecified
        )
    )
}

@Preview
@Composable
private fun EchoJournalChipPreview() {
    EchoJournalTheme {
        EchoJournalChip(
            selected = true,
            onClick = {},
            label = {
                Text("Label")
            },
            avatar = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = StressedIcon,
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = CloseIcon,
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        )
    }
}
