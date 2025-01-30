package com.clarkelamothe.echojournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val cameFromWidget = intent.extras?.getBoolean("WIDGET") ?: false

        setContent {
            EchoJournalTheme {
                val navController = rememberNavController()

                NavigationRoot(
                    autoRecord = cameFromWidget,
                    navController = navController
                )
            }
        }
    }
}
