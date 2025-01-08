package com.clarkelamothe.echojournal.memo.presentation.overview

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalScaffold
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalToolbar
import com.clarkelamothe.echojournal.core.presentation.designsystem.RecordMemoActionButtons
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme

@Composable
fun MemoOverviewScreenRoot(
    onSettingsClick: () -> Unit
) {
    MemoOverviewScreen(
        onAction = {
            when (it) {
                MemoOverviewScreenAction.OnFabClick -> {
//                    TODO()
                }

                MemoOverviewScreenAction.OnSettingsClick -> onSettingsClick()
            }
        }
    )
}

@Composable
fun MemoOverviewScreen(
    onAction: (MemoOverviewScreenAction) -> Unit
) {
    EchoJournalScaffold(
        topAppBar = {
            EchoJournalToolbar(
                title = stringResource(R.string.your_echojournal),
                showSettingsButton = true,
                onSettingsClick = {
                    onAction(MemoOverviewScreenAction.OnSettingsClick)
                }
            )
        },
        floatingActionButton = {
            RecordMemoActionButtons(
                modifier = Modifier.padding(bottom = 40.dp),
                onStartRecording = {},
                onCancelRecording = {},
                onFinishRecording = {}
            )
        }
    ) {

    }
}

@Preview
@Composable
private fun MemoOverviewScreenPreview() {
    EchoJournalTheme {
        MemoOverviewScreen(
            onAction = {}
        )
    }
}
