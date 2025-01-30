package com.clarkelamothe.echojournal

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.clarkelamothe.echojournal.core.presentation.ui.viewModelFactory
import com.clarkelamothe.echojournal.memo.MemoModule
import com.clarkelamothe.echojournal.memo.presentation.create.CreateMemoScreenRoot
import com.clarkelamothe.echojournal.memo.presentation.create.CreateMemoViewModel
import com.clarkelamothe.echojournal.memo.presentation.overview.MemoOverviewScreenRoot
import com.clarkelamothe.echojournal.memo.presentation.overview.MemoOverviewViewModel
import com.clarkelamothe.echojournal.memo.presentation.settings.SettingsScreenRoot
import com.clarkelamothe.echojournal.memo.presentation.settings.SettingsViewModel

@Composable
fun NavigationRoot(
    navController: NavHostController,
    autoRecord: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Memos
    ) {
        navigation<Routes.Memos>(
            startDestination = Routes.MemoOverview
        ) {
            composable<Routes.MemoOverview> {
                val context = LocalContext.current

                val viewModel = viewModel<MemoOverviewViewModel>(
                    factory = viewModelFactory {
                        MemoOverviewViewModel(
                            MemoModule.voiceMemoRepository,
                            MemoModule.player(context),
                            MemoModule.recorder(context)
                        )
                    }
                )

                MemoOverviewScreenRoot(
                    viewModel = viewModel,
                    autoRecord = autoRecord,
                    onSettingsClick = {
                        navController.navigate(Routes.Settings)
                    },
                    onVoiceMemoRecorded = {
                        navController.navigate(Routes.MemoCreate(it))
                    }
                )
            }

            composable<Routes.MemoCreate> {
                val context = LocalContext.current
                val filePath = it.toRoute<Routes.MemoCreate>().filePath

                val viewModel = viewModel<CreateMemoViewModel>(
                    factory = viewModelFactory {
                        CreateMemoViewModel(
                            filePath,
                            MemoModule.player(context),
                            MemoModule.voiceMemoRepository,
                            MemoModule.settingsRepository
                        )
                    }
                )

                CreateMemoScreenRoot(
                    viewModel = viewModel,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }

            composable<Routes.Settings> {
                SettingsScreenRoot(
                    viewModel = viewModel<SettingsViewModel>(
                        factory = viewModelFactory {
                            SettingsViewModel(
                                MemoModule.settingsRepository,
                                MemoModule.voiceMemoRepository
                            )
                        }
                    ),
                    onBackClick = {
                        navController.navigateUp()
                    },

                    )
            }
        }
    }
}
