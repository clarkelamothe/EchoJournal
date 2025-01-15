package com.clarkelamothe.echojournal.core.presentation.ui.mappers

import com.clarkelamothe.echojournal.core.domain.MoodBM
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM

fun MoodBM.toVM() = when (this) {
    MoodBM.Stressed -> MoodVM.Stressed
    MoodBM.Sad -> MoodVM.Sad
    MoodBM.Neutral -> MoodVM.Neutral
    MoodBM.Peaceful -> MoodVM.Peaceful
    MoodBM.Excited -> MoodVM.Excited
}

fun MoodVM.toBM() = when (this) {
    MoodVM.Stressed -> MoodBM.Stressed
    MoodVM.Sad -> MoodBM.Sad
    MoodVM.Peaceful -> MoodBM.Neutral
    MoodVM.Neutral -> MoodBM.Peaceful
    MoodVM.Excited -> MoodBM.Excited
}
