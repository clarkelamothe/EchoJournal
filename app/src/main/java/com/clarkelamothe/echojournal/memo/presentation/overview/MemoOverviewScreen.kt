package com.clarkelamothe.echojournal.memo.presentation.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalScaffold
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.AddIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.ButtonGradient

@Composable
fun MemoOverviewScreenRoot() {
    MemoOverviewScreen(
        onAction = {}
    )
}

@Composable
fun MemoOverviewScreen(
    onAction: (MemoOverviewScreenAction) -> Unit
) {
    EchoJournalScaffold(
        topAppBar = {},
        floatingActionButton = {
            IconButton(
                onClick = {
                    onAction(MemoOverviewScreenAction.OnFabClick)
                },
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .background(
                        brush = ButtonGradient,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = AddIcon,
                    contentDescription = "Add a New Memo",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) {

    }
}

@Preview
@Composable
private fun MemoOverviewScreenPreview() {
    MemoOverviewScreen(
        onAction = {}
    )
}
