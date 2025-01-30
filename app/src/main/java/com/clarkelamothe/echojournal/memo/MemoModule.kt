package com.clarkelamothe.echojournal.memo

import android.content.Context
import com.clarkelamothe.echojournal.EchoJournalApp
import com.clarkelamothe.echojournal.memo.data.AudioPlayerImpl
import com.clarkelamothe.echojournal.memo.data.AudioRecorderImpl
import com.clarkelamothe.echojournal.memo.data.SettingsRepositoryImpl
import com.clarkelamothe.echojournal.memo.data.VoiceMemoRepositoryImpl
import com.clarkelamothe.echojournal.memo.domain.SettingsRepository
import com.clarkelamothe.echojournal.memo.domain.VoiceMemoRepository
import kotlinx.coroutines.Dispatchers

class MemoModule {
    init {
        voiceMemoRepository = VoiceMemoRepositoryImpl()
        settingsRepository = SettingsRepositoryImpl(Dispatchers.IO)
    }

    companion object {
        lateinit var voiceMemoRepository: VoiceMemoRepository
        lateinit var settingsRepository: SettingsRepository
        fun player(context: Context) = AudioPlayerImpl(context, EchoJournalApp.fileManager)
        fun recorder(context: Context) = AudioRecorderImpl(context, EchoJournalApp.fileManager)
    }
}