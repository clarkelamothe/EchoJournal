package com.clarkelamothe.echojournal

import android.app.Application
import com.clarkelamothe.echojournal.di.AppModule
import com.clarkelamothe.echojournal.di.AppModuleImpl

class EchoJournalApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }

    companion object {
        lateinit var appModule: AppModule
    }
}
