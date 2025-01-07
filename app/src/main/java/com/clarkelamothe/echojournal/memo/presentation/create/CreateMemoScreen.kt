package com.clarkelamothe.echojournal.memo.presentation.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalScaffold
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalToolbar
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme

@Composable
fun CreateMemoScreenRoot() {
    CreateMemoScreen()
}

@Composable
fun CreateMemoScreen() {
    EchoJournalScaffold(
        withGradient = false,
        topAppBar = {
            EchoJournalToolbar(
                title = stringResource(R.string.title_create_memo_screen),
                showSettingsButton = false,
                showBackButton = true
            )
        },
        floatingActionButton = {}
    ) {

    }
}

@Preview
@Composable
private fun CreateMemoScreenPreview() {
    EchoJournalTheme {
        CreateMemoScreen()
    }
}
