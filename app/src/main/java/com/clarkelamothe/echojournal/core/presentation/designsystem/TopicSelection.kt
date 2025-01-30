@file:OptIn(ExperimentalLayoutApi::class)

package com.clarkelamothe.echojournal.core.presentation.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.AddIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CheckIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CloseIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.HashtagIcon

@Composable
fun TopicSelection(
    modifier: Modifier = Modifier,
    suggestions: List<String> = emptyList(),
    leadingIcon: @Composable () -> Unit,
    dropdownWidth: Dp,
    textFieldState: TextFieldState,
    initialTopics: List<String>,
    innerBox: @Composable () -> Unit,
    isFocused: Boolean = false,
    isDropdownExpanded: Boolean = false,
    onDismissRequest: () -> Unit,
    onRemoveTopic: (Int) -> Unit,
    onAddTopic: (String) -> Unit

) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        leadingIcon()

        if (textFieldState.text.isEmpty() && !isFocused && initialTopics.isEmpty()) {
            Text(
                text = stringResource(R.string.add_topic),
                color = MaterialTheme.colorScheme.outlineVariant,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        } else {
            with(initialTopics) {
                if (isNotEmpty()) {
                    mapIndexed { index, topic ->
                        Chip(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = topic,
                            selected = true,
                            trailingIcon = {
                                IconButton(
                                    onClick = { onRemoveTopic(index) },
                                    modifier = Modifier
                                        .size(16.dp)
                                        .align(Alignment.CenterVertically)
                                ) {
                                    Icon(
                                        imageVector = CloseIcon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.outlineVariant,
                                    )
                                }
                            }
                        )
                    }
                }
                Box(modifier = Modifier.align(Alignment.CenterVertically)) { innerBox() }
            }
        }

        DropdownMenu(
            modifier = Modifier.width(dropdownWidth),
            expanded = isDropdownExpanded,
            onDismissRequest = onDismissRequest,
            shape = RoundedCornerShape(10.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            properties = PopupProperties(focusable = false)
        ) {
            suggestions.forEach { suggestion ->
                key(suggestion) { suggestion.hashCode() }

                DropdownItem(
                    isSelected = suggestion in initialTopics,
                    item = suggestion,
                    onSelect = {
                        onAddTopic(suggestion)
                        textFieldState.clearText()
                    },
                    leadingIcon = {
                        Icon(
                            tint = Color.Unspecified,
                            imageVector = HashtagIcon,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = CheckIcon,
                            contentDescription = null
                        )
                    }
                )
            }

            if (textFieldState.text !in suggestions) {
                DropdownItem(
                    isSelected = false,
                    item = stringResource(R.string.create, textFieldState.text),
                    onSelect = {
                        onAddTopic(textFieldState.text.toString())
                        textFieldState.clearText()
                    },
                    leadingIcon = {
                        Icon(
                            tint = Color.Unspecified,
                            imageVector = AddIcon,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {}
                )
            }
        }
    }
}

@Composable
fun TopicSelectionAlternate(
    modifier: Modifier = Modifier,
    suggestions: List<String> = emptyList(),
    leadingIcon: @Composable () -> Unit,
    dropdownWidth: Dp,
    textFieldState: TextFieldState,
    initialTopics: List<String>,
    innerBox: @Composable () -> Unit,
    isDropdownExpanded: Boolean = false,
    onDismissRequest: () -> Unit,
    isFocused: Boolean = false,
    onRemoveTopic: (Int) -> Unit,
    onAddTopic: (String) -> Unit
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        with(initialTopics) {
            if (isNotEmpty()) {
                mapIndexed { index, topic ->
                    Chip(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = topic,
                        selected = true,
                        trailingIcon = {
                            IconButton(
                                onClick = { onRemoveTopic(index) },
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .size(16.dp)
                            ) {
                                Icon(
                                    imageVector = CloseIcon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.outlineVariant,
                                )
                            }
                        }
                    )
                }
            }
            leadingIcon()
            if (isFocused) {
                Box(modifier = Modifier.align(Alignment.CenterVertically)) { innerBox() }
            }
        }

        DropdownMenu(
            modifier = Modifier.width(dropdownWidth),
            expanded = isDropdownExpanded,
            onDismissRequest = onDismissRequest,
            shape = RoundedCornerShape(10.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            properties = PopupProperties(focusable = false)
        ) {
            suggestions.forEach { suggestion ->
                key(suggestion) { suggestion.hashCode() }

                DropdownItem(
                    isSelected = suggestion in initialTopics,
                    item = suggestion,
                    onSelect = {
                        onAddTopic(textFieldState.text.toString())
                        textFieldState.clearText()
                    },
                    leadingIcon = {
                        Icon(
                            tint = Color.Unspecified,
                            imageVector = HashtagIcon,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = CheckIcon,
                            contentDescription = null
                        )
                    }
                )
            }

            if (textFieldState.text !in suggestions || suggestions.isNotEmpty()) {
                DropdownItem(
                    isSelected = false,
                    item = stringResource(R.string.create, textFieldState.text),
                    onSelect = {
                        onAddTopic(textFieldState.text.toString())
                        textFieldState.clearText()
                    },
                    leadingIcon = {
                        Icon(
                            tint = Color.Unspecified,
                            imageVector = AddIcon,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {}
                )
            }
        }

    }
}