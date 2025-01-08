package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CloseIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.MicIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.ButtonGradient

@Composable
fun RecordMemoActionButtons(
    modifier: Modifier = Modifier,
    onStartRecording: () -> Unit,
    onCancelRecording: () -> Unit,
    onFinishRecording: () -> Unit
) {
    var canCancel by remember { mutableStateOf(false) }
    val draggableState = rememberDraggableState {
        println(it)
    }
    val micInteraction = remember { MutableInteractionSource() }

    LaunchedEffect(micInteraction) {
        micInteraction.interactions.collect {
            when (it) {
                is PressInteraction.Press -> {
                    canCancel = !canCancel
                    onStartRecording()
                }

                is PressInteraction.Release -> {
                    canCancel = false
                    onFinishRecording()
                }

                is PressInteraction.Cancel -> {
                    onCancelRecording()
                }
            }
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = {
                canCancel = false
            },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = CircleShape
                )
                .size(animateDpAsState(if (canCancel) 40.dp else 0.dp, label = "").value)
        ) {
            Icon(
                modifier = Modifier,
                imageVector = CloseIcon,
                contentDescription = stringResource(R.string.add_a_new_memo),
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }
        Spacer(Modifier.width(animateDpAsState(if (canCancel) 24.dp else 0.dp, label = "").value))

        IconButton(
            interactionSource = micInteraction,
            onClick = {},
            modifier = Modifier
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    spotColor = MaterialTheme.colorScheme.primary
                )
                .background(
                    brush = ButtonGradient,
                    shape = CircleShape
                )
                .size(64.dp)
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Horizontal
                )
        ) {
            Icon(
                imageVector = MicIcon,
                contentDescription = stringResource(R.string.add_a_new_memo),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun RecordMemoPreview() {
    RecordMemoActionButtons(
        onStartRecording = {},
        onCancelRecording = {},
        onFinishRecording = {}
    )
}
