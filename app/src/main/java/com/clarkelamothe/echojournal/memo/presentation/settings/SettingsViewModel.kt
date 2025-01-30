@file:Suppress("OPT_IN_USAGE")

package com.clarkelamothe.echojournal.memo.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM
import com.clarkelamothe.echojournal.memo.domain.SettingsRepository
import com.clarkelamothe.echojournal.memo.domain.VoiceMemoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class SettingsViewModel(
    settingsRepository: SettingsRepository,
    voiceRepository: VoiceMemoRepository
) : ViewModel() {
    var state by mutableStateOf(SettingsState())
        private set

    private val mood = MutableStateFlow<MoodVM?>(null)
    private val topics = MutableStateFlow<List<String>>(emptyList())
    private val suggestions = MutableStateFlow<List<String>>(emptyList())
    private val topicInput = MutableStateFlow("")

    init {
        combine(
            mood,
            topics,
            suggestions
        ) { mood, topics, suggestions ->
            state = state.copy(
                settings = SettingsVM(
                    moodVM = mood,
                    topics = topics.sorted()
                ),
                suggestions = suggestions.sorted()
            )

            settingsRepository.save(
                state.settings.toBM()
            )
        }.launchIn(viewModelScope)

        topicInput
            .flatMapLatest {
                if (it.length >= 2) {
                    voiceRepository.filterTopics(it)
                } else flowOf()
            }
            .onEach {
                suggestions.update { it }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: SettingsScreenAction) {
        when (action) {
            SettingsScreenAction.DismissDropdown -> {
                state = state.copy(dropdownExpanded = false)
            }

            is SettingsScreenAction.OnAddTopic -> {
                topics.update {
                    it.plus(action.topic).distinct()
                }
            }

            is SettingsScreenAction.OnMoodSelect -> {
                mood.update { action.mood }
            }

            is SettingsScreenAction.OnInputTopic -> {
                state = state.copy(
                    inputText = action.text.toString(),
                    dropdownExpanded = action.text.length >= 2
                )
            }

            is SettingsScreenAction.OnRemoveTopic -> {
                topics.update {
                    val list = it.toMutableStateList()
                    list.removeAt(action.index)
                    list
                }
            }

            else -> {
                /* No-Op */
            }
        }
    }
}

data class SettingsState(
    val settings: SettingsVM = SettingsVM(),
    val dropdownExpanded: Boolean = false,
    val suggestions: List<String> = emptyList(),
    val inputText: String = ""
)