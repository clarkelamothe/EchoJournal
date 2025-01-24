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
    fun moodLabel(selectedMoods: List<MoodVM>) =
        if (selectedMoods.isEmpty() || selectedMoods.size == MoodVM.entries.size)
            "All Moods"
        else {
            selectedMoods.joinToString(separator = ", ") {
                it.title
            }
        }

    fun topicsLabel(selectedTopics: List<String>, initialTopics: List<String>) = if (
        selectedTopics.isEmpty() || selectedTopics.size == initialTopics.size
    ) "All Topics" else {
        with(selectedTopics.take(2)) {
            if (selectedTopics.size <= 2) {
                joinToString(", ") { it }
            } else {
                joinToString(", ") {
                    it
                } + " +${selectedTopics.size - 2}"
            }
        }
    }
}
