package com.clarkelamothe.echojournal.core.presentation.ui.mappers

import com.clarkelamothe.echojournal.core.domain.Mood
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM

fun Mood.toVM() = when (this) {
    Mood.Stressed -> MoodVM.Stressed
    Mood.Sad -> MoodVM.Sad
    Mood.Neutral -> MoodVM.Neutral
    Mood.Peaceful -> MoodVM.Peaceful
    Mood.Excited -> MoodVM.Excited
}

fun MoodVM.toBM() = when (this) {
    MoodVM.Stressed -> Mood.Stressed
    MoodVM.Sad -> Mood.Sad
    MoodVM.Peaceful -> Mood.Peaceful
    MoodVM.Neutral -> Mood.Neutral
    MoodVM.Excited -> Mood.Excited
}
