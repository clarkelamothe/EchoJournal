package com.clarkelamothe.echojournal.di

import android.content.Context
import com.clarkelamothe.echojournal.memo.presentation.overview.MemoOverviewViewModel

interface AppModule {
    val memoOverviewViewModel: MemoOverviewViewModel
}

class AppModuleImpl(
    private val appContext: Context
) : AppModule {
    override val memoOverviewViewModel: MemoOverviewViewModel = MemoOverviewViewModel()
}
