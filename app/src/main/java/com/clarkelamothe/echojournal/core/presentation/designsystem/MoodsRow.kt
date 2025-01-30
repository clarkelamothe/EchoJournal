package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM

@Composable
fun MoodsRow(
    modifier: Modifier = Modifier,
    selectedMood: MoodVM?,
    onSelectMood: (MoodVM) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MoodVM.entries.map {
            Column(
                modifier = Modifier.size(64.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        onSelectMood(it)
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    if (selectedMood == it) {
                        Icon(
                            imageVector = ImageVector.vectorResource(it.icon),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(it.iconOutlined),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                }
                Text(
                    text = it.title,
                    textAlign = TextAlign.Center,
                    color = if (selectedMood == it) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.outline
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}