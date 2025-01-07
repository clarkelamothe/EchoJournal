package com.clarkelamothe.echojournal

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Memos : Routes

    @Serializable
    data object MemoOverview : Routes

    @Serializable
    data object MemoNew : Routes

    @Serializable
    data object Settings : Routes
}
