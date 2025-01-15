package com.clarkelamothe.echojournal.core.presentation.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Excited25
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Excited35
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Excited80
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Excited95
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.ExcitedGradient
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Neutral25
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Neutral35
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Neutral80
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Neutral95
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.NeutralGradient
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Peaceful25
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Peaceful35
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Peaceful80
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Peaceful95
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.PeacefulGradient
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Sad25
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Sad35
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Sad80
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Sad95
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.SadGradient
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Stressed25
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Stressed35
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Stressed80
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Stressed95
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.StressedGradient

enum class MoodVM(
    val title: String,
    val color25: Color,
    val color35: Color,
    val color80: Color,
    val color95: Color,
    val brush: Brush,
    @DrawableRes val icon: Int,
    @DrawableRes val iconOutlined: Int
) {
    Stressed(
        "Stressed",
        Stressed25,
        Stressed35,
        Stressed80,
        Stressed95,
        StressedGradient,
        R.drawable.ic_mood_stressed_active,
        R.drawable.ic_stressed_outline
    ),
    Sad(
        "Sad",
        Sad25,
        Sad35,
        Sad80,
        Sad95,
        SadGradient,
        R.drawable.ic_mood_sad_active,
        R.drawable.ic_sad_outline
    ),
    Peaceful(
        "Peaceful",
        Peaceful25,
        Peaceful35,
        Peaceful80,
        Peaceful95,
        PeacefulGradient,
        R.drawable.ic_mood_peaceful_active,
        R.drawable.ic_peace_outline
    ),
    Neutral(
        "Neutral",
        Neutral25,
        Neutral35,
        Neutral80,
        Neutral95,
        NeutralGradient,
        R.drawable.ic_mood_neutral_active,
        R.drawable.ic_neutral_outline
    ),
    Excited(
        "Excited",
        Excited25,
        Excited35,
        Excited80,
        Excited95,
        ExcitedGradient,
        R.drawable.ic_mood_excited_active,
        R.drawable.ic_excited_outline
    )
}
