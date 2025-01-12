package com.clarkelamothe.echojournal.memo.presentation.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.EmptyIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme

@Composable
fun EmptyStateScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = EmptyIcon,
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(Modifier.height(34.dp))
        Text(
            text = "No Entries",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Start recording your first Echo ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun EmptyStateScreenPreview() {
    EchoJournalTheme {
        EmptyStateScreen(
            Modifier.fillMaxSize()
        )
    }
}