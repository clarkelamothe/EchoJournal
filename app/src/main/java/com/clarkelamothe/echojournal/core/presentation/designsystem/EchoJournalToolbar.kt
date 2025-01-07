package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EchoJournalToolbar(
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    showSettingsButton: Boolean = false,
    title: String,
    onBackClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarColors(
            containerColor = Color.Unspecified,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.secondary
        ),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = if (showBackButton) TextAlign.Center else TextAlign.Start,
                style = if (showBackButton) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.headlineLarge
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        tint = Color.Unspecified,
                        contentDescription = stringResource(R.string.icon_back_description)
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier.size(40.dp)
            ) {
                if (showSettingsButton) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        tint = Color.Unspecified,
                        contentDescription = stringResource(R.string.icon_settings_description)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun EchoJournalToolbarPreview() {
    EchoJournalTheme {
        EchoJournalToolbar(
            title = "Your EchoJournal",
            showSettingsButton = true,
            onSettingsClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EchoJournalToolbarPreviewWithBackButton() {
    EchoJournalTheme {
        EchoJournalToolbar(
            title = "New Entry",
            showBackButton = true,
            onSettingsClick = {},
            onBackClick = {}
        )
    }
}
