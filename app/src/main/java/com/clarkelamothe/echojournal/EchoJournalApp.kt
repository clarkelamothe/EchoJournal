package com.clarkelamothe.echojournal

import android.app.Application
import com.clarkelamothe.echojournal.core.database.EchoJournalDatabase
import com.clarkelamothe.echojournal.memo.MemoModule
import com.clarkelamothe.echojournal.memo.data.FileManagerImpl
import com.clarkelamothe.echojournal.memo.domain.FileManager

class EchoJournalApp : Application() {

    override fun onCreate() {
        super.onCreate()
        db = EchoJournalDatabase.db(this)
        fileManager = FileManagerImpl(this)
        memoModule = MemoModule()
    }

    companion object {
        lateinit var db: EchoJournalDatabase
        lateinit var fileManager: FileManager
        lateinit var memoModule: MemoModule
    }
}
