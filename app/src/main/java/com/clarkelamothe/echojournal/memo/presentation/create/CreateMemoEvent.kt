package com.clarkelamothe.echojournal.memo.presentation.create

sealed interface CreateMemoEvent {
    data object MemoSaved : CreateMemoEvent
    data object MemoCancelled : CreateMemoEvent
}