package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CheckIcon

@Composable
fun DropdownItem(
    isSelected: Boolean,
    item: String,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit,
    onSelect: () -> Unit
) {
    DropdownMenuItem(
        modifier = Modifier
            .padding(4.dp)
            .border(
                0.dp,
                Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected)
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                else Color.Transparent
            ),
        colors = MenuItemColors(
            textColor = MaterialTheme.colorScheme.secondary,
            leadingIconColor = Color.Unspecified,
            trailingIconColor = Color.Unspecified,
            disabledTextColor = Color.Unspecified,
            disabledLeadingIconColor = Color.Unspecified,
            disabledTrailingIconColor = Color.Unspecified
        ),
        text = { Text(text = item) },
        onClick = onSelect,
        leadingIcon = leadingIcon,
        trailingIcon = { if (isSelected) trailingIcon() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxScope.EchoJournalDropdownMenu(
    expanded: Boolean = false,
    onDismissRequest: () -> Unit,
    items: List<String>,
    shouldAnimate: Boolean,
    onSelect: () -> Unit,
    leadingIcon: @Composable () -> Unit
) {
    ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(10.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEach { topic ->
            key(topic) { topic.hashCode() }

            AnimatedContent(
                targetState = shouldAnimate,
                label = "Animate the selected item"
            ) { isTopicSelected ->
                DropdownItem(
                    isSelected = isTopicSelected,
                    item = topic,
                    onSelect = onSelect,
                    leadingIcon = leadingIcon,
                    trailingIcon = {
                        Icon(
                            imageVector = CheckIcon,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}