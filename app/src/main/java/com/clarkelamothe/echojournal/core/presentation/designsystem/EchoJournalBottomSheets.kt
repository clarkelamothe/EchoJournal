package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CheckIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CloseIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.MicIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.PauseIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.ButtonGradient
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Primary95
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.SurfaceTint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordingBottomSheet(
    modifier: Modifier = Modifier,
    state: RecordingState,
    title: String = "",
    elapsedTime: String = "",
    onDismissRequest: () -> Unit,
    cancelRecording: () -> Unit,
    pauseRecording: () -> Unit,
    finishRecording: () -> Unit,
    resumeRecording: () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 16.dp,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .width(32.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )
        }
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = elapsedTime,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.height(30.dp))

        VoiceRecorder(
            state = state,
            pauseRecording = pauseRecording,
            finishRecording = finishRecording,
            cancelRecording = cancelRecording,
            resumeRecording = resumeRecording
        )
        Spacer(Modifier.height(22.dp))
    }
}

enum class RecordingState {
    Recording,
    Paused
}

@Composable
private fun VoiceRecorder(
    state: RecordingState,
    pauseRecording: () -> Unit,
    finishRecording: () -> Unit,
    cancelRecording: () -> Unit,
    resumeRecording: () -> Unit
) {
    var currentAmplitude by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            )
        ) { value, _ ->
            currentAmplitude = value
        }
    }

    val maxScale by remember { mutableFloatStateOf(1.2f) }
    val minScale by remember { mutableFloatStateOf(0.8f) }
    val maxAlpha by remember { mutableFloatStateOf(0.3f) }
    val minAlpha by remember { mutableFloatStateOf(0f) }

    val scale by remember {
        derivedStateOf {
            (currentAmplitude * (maxScale - minScale) + minScale).coerceIn(minScale, maxScale)
        }
    }

    val rippleAlpha by remember {
        derivedStateOf {
            (currentAmplitude * maxAlpha).coerceIn(minAlpha, maxAlpha)
        }
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = cancelRecording,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = CircleShape
                )
                .size(48.dp)
        ) {
            Icon(
                imageVector = CloseIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onErrorContainer
            )
        }

        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state == RecordingState.Recording) {
                repeat(2) { index ->
                    val offsetScale = 1f + (index * 0.3f)
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .scale(scale * offsetScale)
                            .alpha(rippleAlpha / index)
                            .background(
                                color = Primary95,
                                shape = CircleShape
                            )
                    )
                }
            }

            IconButton(
                onClick = {
                    when (state) {
                        RecordingState.Recording -> finishRecording()
                        RecordingState.Paused -> resumeRecording()
                    }
                },
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
                    .size(72.dp)
            ) {
                Icon(
                    imageVector = when (state) {
                        RecordingState.Recording -> CheckIcon
                        RecordingState.Paused -> MicIcon
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        IconButton(
            onClick = {
                when (state) {
                    RecordingState.Recording -> pauseRecording()
                    RecordingState.Paused -> finishRecording()
                }
            },
            modifier = Modifier
                .background(
                    color = SurfaceTint.copy(alpha = 0.12f),
                    shape = CircleShape
                )
                .size(48.dp)
        ) {
            Icon(
                imageVector = when (state) {
                    RecordingState.Recording -> PauseIcon
                    RecordingState.Paused -> CheckIcon
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
