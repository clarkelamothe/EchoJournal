package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MoodIconsRow(
    icons: List<ImageVector>
) {
    Box(
        modifier = Modifier.padding(4.dp)
    ) {
        icons.mapIndexed { index, icon ->
            Icon(
                modifier = Modifier
                    .padding(
                        start = if (index > 0) 21.dp.times(index) else 0.dp
                    )
                    .size(24.dp),
                imageVector = icon,
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}