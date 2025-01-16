package com.clarkelamothe.echojournal.memo.domain

interface AudioPlayer {
    fun playFile(filePath: String, onComplete: () -> Unit)
    fun stop()
    fun pause()
    fun resume()
}
