package com.clarkelamothe.echojournal.memo.presentation.overview

import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM

data class FilterState(
    val moodChipLabel: String = "All Moods",
    val topicChipLabel: String = "All Topics",
    val moods: List<MoodVM> = MoodVM.entries,
    val topics: List<String> = emptyList(),
    val selectedMoods: List<MoodVM> = emptyList(),
    val selectedTopics: List<String> = emptyList()
) {

}