package com.clarkelamothe.echojournal.core.presentation.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    surface = Primary100,
    surfaceVariant = SurfaceVariant,
    inverseOnSurface = Secondary95,
    onSurface = NeutralVariant10,
    onSurfaceVariant = NeutralVariant30,
    outline = NeutralVariant50,
    outlineVariant = NeutralVariant80,
    primary = Primary30,
    primaryContainer = Primary50,
    onPrimary = Primary100,
    inversePrimary = Secondary80,
    secondary = Secondary30,
    secondaryContainer = Secondary50,
    errorContainer = Error95,
    onErrorContainer = Error20,
    onError = Error100,
    background = NeutralVariant99,
    surfaceTint = SurfaceTint
)

@Composable
fun EchoJournalTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}