package com.clarkelamothe.echojournal

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.clarkelamothe.echojournal.memo.presentation.create.CreateMemoScreenRoot
import com.clarkelamothe.echojournal.memo.presentation.overview.MemoOverviewScreenRoot
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
                MemoOverviewScreenRoot(
                    onSettingsClick = {
                        navController.navigate(Routes.Settings)
                    }
                )
            }

            composable<Routes.MemoCreate> {
                CreateMemoScreenRoot()
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
