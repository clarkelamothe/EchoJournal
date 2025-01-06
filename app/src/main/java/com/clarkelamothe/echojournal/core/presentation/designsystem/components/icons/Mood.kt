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
