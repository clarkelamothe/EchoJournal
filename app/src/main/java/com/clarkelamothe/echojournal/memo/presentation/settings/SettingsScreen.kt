package com.clarkelamothe.echojournal.memo.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalScaffold
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalToolbar

@Composable
fun SettingsScreenRoot(
    onBackClick: () -> Unit
) {
    SettingsScreen(
        onAction = {
            when (it) {
                SettingsScreenAction.OnBackClick -> onBackClick()
            }
        }
    )
}

@Composable
fun SettingsScreen(
    onAction: (SettingsScreenAction) -> Unit
) {
    EchoJournalScaffold(
        topAppBar = {
            EchoJournalToolbar(
                title = stringResource(R.string.settings),
                showBackButton = true,
                onBackClick = {
                    onAction(SettingsScreenAction.OnBackClick)
                }
            )
        },
        floatingActionButton = {}
    ) {

    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(
        onAction = {}
    )
}