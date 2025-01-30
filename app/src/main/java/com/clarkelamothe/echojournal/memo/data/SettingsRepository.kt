package com.clarkelamothe.echojournal.memo.data

import com.clarkelamothe.echojournal.EchoJournalApp
import com.clarkelamothe.echojournal.core.database.SettingsDao
import com.clarkelamothe.echojournal.core.domain.Settings
import com.clarkelamothe.echojournal.memo.domain.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val settingsDao: SettingsDao = EchoJournalApp.db.settingsDao()
) : SettingsRepository {
    override suspend fun save(settings: Settings) = withContext(ioDispatcher) {
        settingsDao.upsert(settings.toDM())
    }
}
