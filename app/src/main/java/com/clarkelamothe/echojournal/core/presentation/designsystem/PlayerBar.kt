package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.PauseIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.PlayIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme

@Composable
fun PlayerBar(
    modifier: Modifier = Modifier,
    playerState: PlayerState,
    containerColor: Color = MaterialTheme.colorScheme.inverseOnSurface,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    timeStamp: String,
    progress: Float = 0.8f,
    onClickPlay: () -> Unit,
    onClickPause: () -> Unit,
    onClickResume: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {},
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 8.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            IconButton(
                onClick = {
                    when (playerState) {
                        PlayerState.Idle -> onClickPlay()
                        PlayerState.Playing -> onClickPause()
                        PlayerState.Paused -> onClickResume()
                    }
                },
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        shape = CircleShape,
                        spotColor = MaterialTheme.colorScheme.primary
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    )
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = when (playerState) {
                        PlayerState.Idle -> PlayIcon
                        PlayerState.Playing -> PauseIcon
                        PlayerState.Paused -> PlayIcon
                    },
                    contentDescription = stringResource(R.string.add_a_mood),
                    tint = iconColor.copy(0.9f)
                )
            }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { progress },
                    color = iconColor.copy(0.6f),
                    trackColor = iconColor.copy(alpha = 0.2f),
                    strokeCap = StrokeCap.Round,
                    drawStopIndicator = {},
                    gapSize = 0.dp
                )
            }
            Text(
                text = timeStamp,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

enum class PlayerState {
    Idle,
    Playing,
    Paused
}

@Preview
@Composable
private fun PlayerBarPreview() {
    EchoJournalTheme {
        PlayerBar(
            playerState = PlayerState.Paused,
            onClickPlay = {},
            onClickPause = {},
            timeStamp = "7:05/12:30",
            onClickResume = {}
        )
    }
}