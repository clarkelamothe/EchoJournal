package com.clarkelamothe.echojournal.memo.presentation.create

sealed interface CreateMemoAction {
    data object OnAddMoodClick : CreateMemoAction
    data object OnPlayClick : CreateMemoAction
    data object OnAiClick : CreateMemoAction
    data object OnCancelClick : CreateMemoAction
    data object OnSaveClick : CreateMemoAction
    data object OnBackClick : CreateMemoAction
}
