package com.clarkelamothe.echojournal.memo.domain

interface AudioRecorder {
    fun start(fileName: String): String
    fun stop()
    fun pause()
    fun resume()
    fun maxAmp(): Float
}