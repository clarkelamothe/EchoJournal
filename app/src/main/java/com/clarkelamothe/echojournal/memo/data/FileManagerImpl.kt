package com.clarkelamothe.echojournal.memo.data

import android.content.Context
import com.clarkelamothe.echojournal.memo.domain.FileManager
import java.io.File

class FileManagerImpl(
    private val context: Context
) : FileManager {
    override fun createTempFile(prefix: String, suffix: String): File =
        File.createTempFile(prefix, suffix)

    override fun moveAndRenameFile(
        filePathToMove: String,
        newName: String,
        newExtension: String
    ): File {
        val old = File(filePathToMove)
        val newFile = File(
            context.filesDir,
            newName
        )
        old.renameTo(newFile)
        old.delete()
        return newFile
    }

    override fun getFile(filePath: String) = File(filePath)
}
