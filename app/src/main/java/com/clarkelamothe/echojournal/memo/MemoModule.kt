package com.clarkelamothe.echojournal.memo

import android.content.Context
import com.clarkelamothe.echojournal.EchoJournalApp
import com.clarkelamothe.echojournal.memo.data.AudioPlayerImpl
import com.clarkelamothe.echojournal.memo.data.AudioRecorderImpl
import com.clarkelamothe.echojournal.memo.data.VoiceMemoRepositoryImpl
import com.clarkelamothe.echojournal.memo.domain.VoiceMemoRepository

class MemoModule {
    init {
        voiceMemoRepository = VoiceMemoRepositoryImpl()
    }

    companion object {
        lateinit var voiceMemoRepository: VoiceMemoRepository
        fun player(context: Context) = AudioPlayerImpl(context, EchoJournalApp.fileManager)
        fun recorder(context: Context) = AudioRecorderImpl(context, EchoJournalApp.fileManager)
    }
}