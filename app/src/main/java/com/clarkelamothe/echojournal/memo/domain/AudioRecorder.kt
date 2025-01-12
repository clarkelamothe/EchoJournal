package com.clarkelamothe.echojournal.memo.domain

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}