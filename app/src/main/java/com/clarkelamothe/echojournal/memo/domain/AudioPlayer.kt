package com.clarkelamothe.echojournal.memo.domain

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}