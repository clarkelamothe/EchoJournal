package com.clarkelamothe.echojournal.memo

import com.clarkelamothe.echojournal.memo.data.VoiceMemoRepositoryImpl
import com.clarkelamothe.echojournal.memo.domain.VoiceMemoRepository

class MemoModule {
    init {
        voiceMemoRepository = VoiceMemoRepositoryImpl()
    }

    companion object {
        lateinit var voiceMemoRepository: VoiceMemoRepository
    }
}