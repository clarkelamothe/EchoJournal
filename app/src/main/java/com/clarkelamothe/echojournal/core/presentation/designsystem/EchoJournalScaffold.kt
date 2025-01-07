package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.BgGradient

@Composable
fun EchoJournalScaffold(
    modifier: Modifier = Modifier,
    withGradient: Boolean = true,
    topAppBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topAppBar,
        containerColor = MaterialTheme.colorScheme.surface,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.End
    ) {
        content.apply {
            if (withGradient) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BgGradient)
                ) { invoke(it) }
            } else invoke(it)
        }
    }
}
