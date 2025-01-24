package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TextExpand(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = 3,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    onExpand: () -> Unit
) {
    FlowRow(
        modifier = modifier.fillMaxSize(),
        maxLines = maxLines,
        overflow = FlowRowOverflow.expandOrCollapseIndicator(
            expandIndicator = {
                Text(
                    style = style,
                    text = "... Show more",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        onExpand()
                    }
                )
            },
            collapseIndicator = {}
        )
    ) {
        text.split(" ").forEach { word ->
            Text(
                text = word,
                style = style,
                modifier = Modifier.padding(
                    end = 2.dp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TextWithShowMorePreview() {
    EchoJournalTheme {
        TextExpand(
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
            maxLines = 3,
            onExpand = {}
        )
    }
}
