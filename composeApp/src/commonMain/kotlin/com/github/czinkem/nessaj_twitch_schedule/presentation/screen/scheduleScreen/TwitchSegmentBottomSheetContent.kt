package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.czinkem.nessaj_twitch_schedule.domain.AppProvider
import com.github.czinkem.nessaj_twitch_schedule.domain.NotificationManager
import kotlinx.datetime.LocalDateTime
import org.koin.compose.koinInject

data class TwitchSegmentBottomSheetContentState(
    val title: String,
    val categoryName: String,
    val isLive: Boolean,
    val time: LocalDateTime,
)

@Composable
fun TwitchSegmentBottomSheetContent(
    modifier: Modifier = Modifier,
    state: TwitchSegmentBottomSheetContentState,
) {
    val appLauncher = koinInject<AppProvider>()
    val notificationManager = koinInject<NotificationManager>()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = state.title,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,

            )
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = state.categoryName
        )
        Divider(
            modifier = Modifier.fillMaxWidth(.9f).padding(vertical = 16.dp)
        )
        if (state.isLive) {
            Button(
                onClick = {
                    appLauncher.openTwitchApp()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Android,
                    contentDescription = null
                )
                Text(
                    text = "Open in App"
                )
            }
        }else {
            OutlinedButton(
                border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                onClick = {
                    notificationManager.setNotificationAtTime(state.time)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Set notification"
                )
            }
        }
    }
}