package com.clarkelamothe.echojournal.memo.presentation.widget

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.padding
import androidx.glance.layout.wrapContentSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.clarkelamothe.echojournal.MainActivity
import com.clarkelamothe.echojournal.R

class EchoJournalAppWidget : GlanceAppWidget() {
    override val sizeMode: SizeMode
        get() = SizeMode.Single

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Box(
                modifier = GlanceModifier
                    .wrapContentSize()
                    .background(
                        imageProvider = ImageProvider(R.drawable.widget)
                    )
                    .padding(start = 8.dp)
                    .clickable(
                        onClick = actionStartActivity<MainActivity>(
                            parameters = actionParametersOf(ActionParameters.Key<Boolean>("WIDGET") to true)
                        )
                    ),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = "How are\nyou doing?",
                    modifier = GlanceModifier
                        .padding(20.dp),
                    style = TextStyle(
                        color = ColorProvider(MaterialTheme.colorScheme.onSurface),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}
