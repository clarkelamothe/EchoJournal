package com.clarkelamothe.echojournal

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.clarkelamothe.echojournal.core.presentation.ui.viewModelFactory
import com.clarkelamothe.echojournal.memo.presentation.create.CreateMemoScreenRoot
import com.clarkelamothe.echojournal.memo.presentation.create.CreateMemoViewModel
import com.clarkelamothe.echojournal.memo.presentation.overview.MemoOverviewScreenRoot
import com.clarkelamothe.echojournal.memo.presentation.overview.MemoOverviewViewModel
import com.clarkelamothe.echojournal.memo.presentation.settings.SettingsScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Memos
    ) {
        navigation<Routes.Memos>(
            startDestination = Routes.MemoOverview
        ) {
            composable<Routes.MemoOverview> {
                val viewModel = viewModel<MemoOverviewViewModel>(
                    factory = viewModelFactory {
                        MemoOverviewViewModel()
                    }
                )

                MemoOverviewScreenRoot(
                    viewModel = viewModel,
                    onSettingsClick = {
                        navController.navigate(Routes.Settings)
                    },
                    onVoiceMemoRecorded = {
                        navController.navigate(Routes.MemoCreate)
                    }
                )
            }

            composable<Routes.MemoCreate> {
                val viewModel = viewModel<CreateMemoViewModel>(
                    factory = viewModelFactory {
                        CreateMemoViewModel()
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
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}
