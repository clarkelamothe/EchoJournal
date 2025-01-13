package com.clarkelamothe.echojournal.memo.presentation.overview

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PermissionDialog(
    modifier: Modifier,
    permission: String,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        dismissButton = {

        },
        confirmButton = {

        },
        title = {

        },
        text = {

        }
    )
}
