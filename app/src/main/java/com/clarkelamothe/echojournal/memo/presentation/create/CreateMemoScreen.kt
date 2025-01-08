package com.clarkelamothe.echojournal.memo.presentation.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalScaffold
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalToolbar
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme

@Composable
fun CreateMemoScreenRoot(
    onBackClick: () -> Unit
) {
    CreateMemoScreen(
        onBackClick
    )
}

@Composable
fun CreateMemoScreen(
    onAction: () -> Unit
) {
    EchoJournalScaffold(
        withGradient = false,
        topAppBar = {
            EchoJournalToolbar(
                title = stringResource(R.string.title_create_memo_screen),
                showSettingsButton = false,
                showBackButton = true,
                onBackClick = onAction
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
        CreateMemoScreen(
            onAction = {}
        )
    }
}
