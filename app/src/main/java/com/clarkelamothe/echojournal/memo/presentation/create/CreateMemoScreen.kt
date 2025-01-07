package com.clarkelamothe.echojournal.memo.presentation.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalScaffold
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalToolbar
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.AddIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.ButtonGradient

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
        floatingActionButton = {
            IconButton(
                onClick = {

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
                    contentDescription = stringResource(R.string.add_a_new_memo),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) {

    }
}

@Preview
@Composable
private fun CreateMemoScreenPreview() {
    CreateMemoScreen()
}
