package com.clarkelamothe.echojournal.memo.domain

interface AudioPlayer {
    fun init(filePath: String)
    fun start(onComplete: () -> Unit)
    fun stop()
    fun pause()
    fun resume()
    fun duration(): Int
}
