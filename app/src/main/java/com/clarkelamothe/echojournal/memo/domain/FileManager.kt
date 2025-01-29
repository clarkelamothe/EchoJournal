package com.clarkelamothe.echojournal.memo.domain

import java.io.File

interface FileManager {
    fun createTempFile(prefix: String, suffix: String): File
    fun moveAndRenameFile(filePathToMove: String, newName: String, newExtension: String): File
    fun getFile(filePath: String): File?
}