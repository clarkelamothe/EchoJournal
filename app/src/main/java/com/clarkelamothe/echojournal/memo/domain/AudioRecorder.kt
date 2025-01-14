package com.clarkelamothe.echojournal.memo.domain

interface AudioRecorder {
    fun start(fileName: String)
    fun stop()
    fun pause()
    fun resume()
}