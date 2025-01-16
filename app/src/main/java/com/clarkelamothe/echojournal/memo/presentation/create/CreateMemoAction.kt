package com.clarkelamothe.echojournal.memo.presentation.create

import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM

sealed interface CreateMemoAction {
    data object OnAddMoodClick : CreateMemoAction
    data object OnPlayClick : CreateMemoAction
    data object OnAiClick : CreateMemoAction
    data object OnCancelClick : CreateMemoAction
    data object OnSaveClick : CreateMemoAction
    data object OnBackClick : CreateMemoAction
    data object OnCancelMoodClick : CreateMemoAction
    data object OnConfirmMoodClick : CreateMemoAction
    data object DismissBottomSheet : CreateMemoAction
    data class OnSelectMood(val moodVM: MoodVM) : CreateMemoAction
    data class OnTitleChange(val title: String) : CreateMemoAction
    data class OnRemoveTopic(val index: Int) : CreateMemoAction
    data class OnAddTopic(val topic: String) : CreateMemoAction
}
