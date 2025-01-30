@file:Suppress("OPT_IN_USAGE")

package com.clarkelamothe.echojournal.memo.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarkelamothe.echojournal.core.presentation.ui.mappers.toVM
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
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    voiceRepository: VoiceMemoRepository
) : ViewModel() {
    var state by mutableStateOf(SettingsState())
        private set

    private val mood = MutableStateFlow<MoodVM?>(null)
    private val topics = MutableStateFlow<List<String>>(emptyList())
    private val suggestions = MutableStateFlow<List<String>>(emptyList())
    private val topicInput = MutableStateFlow("")

    init {
        settingsRepository.get()
            .onEach { setting ->
                topics.update { setting.topics }
                mood.update {
                    setting.mood?.toVM()
                }
            }
            .launchIn(viewModelScope)

        combine(
            mood,
            topics,
            suggestions,
            topicInput
        ) { mood, topics, suggestions, topicInput->
            val settings = state.settings
            state = state.copy(
                settings = settings.copy(
                    moodVM = mood,
                    topics = topics
                ),
                suggestions = suggestions.sorted(),
                dropdownExpanded = topicInput.length >= 2
            )
        }.launchIn(viewModelScope)

        topicInput
            .flatMapLatest {
                if (it.length >= 2) {
                    voiceRepository.filterTopics(it)
                } else flowOf()
            }
            .onEach { topics ->
                suggestions.update { topics }
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
                saveSettings()
            }

            is SettingsScreenAction.OnMoodSelect -> {
                mood.update { action.mood }
                saveSettings()
            }

            is SettingsScreenAction.OnInputTopic -> {
                topicInput.update {
                    action.text.toString()
                }
            }

            is SettingsScreenAction.OnRemoveTopic -> {
                topics.update {
                    val list = it.toMutableStateList()
                    list.removeAt(action.index)
                    list
                }
                saveSettings()
            }

            else -> {
                /* No-Op */
            }
        }
    }

    private fun saveSettings() {
        viewModelScope.launch {
            settingsRepository.save(
                state.settings.toBM()
            )
        }
    }
}

data class SettingsState(
    val settings: SettingsVM = SettingsVM(),
    val dropdownExpanded: Boolean = false,
    val suggestions: List<String> = emptyList(),
    val inputText: String = ""
)