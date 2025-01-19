package com.clarkelamothe.echojournal.memo.domain

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface AudioPlayer {
    fun init(filePath: String)
    fun start(onComplete: () -> Unit)
    fun stop()
    fun pause()
    fun resume()
    fun duration(): Duration
    fun observerPosition(start: Boolean): Flow<Duration>
}
