package com.clarkelamothe.echojournal

import android.app.Application
import com.clarkelamothe.echojournal.core.database.EchoJournalDatabase
import com.clarkelamothe.echojournal.memo.MemoModule

class EchoJournalApp : Application() {
    override fun onCreate() {
        super.onCreate()
        db = EchoJournalDatabase.db(this)
        memoModule = MemoModule()
    }

    companion object {
        lateinit var db: EchoJournalDatabase
        lateinit var memoModule: MemoModule
    }
}
