package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CheckIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.ButtonGradient
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.SurfaceTint
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.SurfaceVariant
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MoodsBottomSheet(
    canSave: Boolean,
    onDismissRequest: () -> Unit,
    onSelectMood: (MoodVM) -> Unit,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    var moodVM: MoodVM? by remember {
        mutableStateOf(null)
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(),
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
            text = stringResource(R.string.how_are_you_doing),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
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
                            moodVM = it
                            onSelectMood(it)
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        if (moodVM == it) {
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
                        color = if (canSave) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.outline
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SurfaceTint.copy(alpha = 0.12f),
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.cancel)
                )
            }

            Button(
                enabled = canSave,
                modifier = Modifier
                    .weight(1f)
                    .shadow(
                        elevation = 8.dp,
                        shape = ButtonDefaults.shape,
                        spotColor = MaterialTheme.colorScheme.primary
                    )
                    .then(
                        if (canSave) Modifier.background(
                            brush = ButtonGradient,
                            shape = ButtonDefaults.shape
                        ) else Modifier.background(
                            color = SurfaceVariant,
                            shape = ButtonDefaults.shape
                        )
                    ),
                onClick = onConfirm,
                colors = ButtonColors(
                    disabledContainerColor = SurfaceVariant,
                    containerColor = Color.Unspecified,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.outline
                ),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = CheckIcon,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.confirm)
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))
    }
}
