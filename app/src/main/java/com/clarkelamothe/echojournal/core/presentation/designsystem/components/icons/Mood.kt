package com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.clarkelamothe.echojournal.R

val ExcitedIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_mood_excited_active)

val NeutralIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_mood_neutral_active)

val PeacefulIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_mood_peaceful_active)

val SadIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_mood_sad_active)

val StressedIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_mood_stressed_active)

val ExcitedIconOutline: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_excited_outline)

val NeutralIconOutline: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_neutral_outline)

val PeacefulIconOutline: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_peace_outline)

val SadIconOutline: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_sad_outline)

val StressedIconOutline: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.ic_stressed_outline)
