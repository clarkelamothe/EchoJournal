package com.clarkelamothe.echojournal.memo.presentation.settings

import androidx.compose.runtime.Stable
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM

@Stable
data class SettingsVM(
    val moodVM: MoodVM? = null,
    val topics: List<String> = emptyList()
)
