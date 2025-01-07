package com.clarkelamothe.echojournal.core.presentation.designsystem.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val BgGradient = Brush.verticalGradient(
    colors = listOf(
        Secondary90.copy(alpha = 0.4f),
        Secondary95.copy(alpha = 0.4f)
    )
)

val BgSaturatedGradient = Brush.verticalGradient(
    colors = listOf(
        Secondary90,
        Secondary95
    )
)

val ButtonGradient = Brush.verticalGradient(
    colors = listOf(
        Primary60,
        Primary50
    )
)

val ButtonPressedGradient = Brush.verticalGradient(
    colors = listOf(
        Primary60,
        Primary40
    )
)

val StressedGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF69193),
        Color(0xFFED3A3A)
    )
)

val SadGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF7BBCFA),
        Color(0xFF2993F7)
    )
)

val NeutralGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFC4F3DB),
        Color(0xFF71EBAC)
    )
)

val PeacefulGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFFCCDEE),
        Color(0xFFF991E0)
    )
)

val ExcitedGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF5CB6F),
        Color(0xFFF6B01A)
    )
)