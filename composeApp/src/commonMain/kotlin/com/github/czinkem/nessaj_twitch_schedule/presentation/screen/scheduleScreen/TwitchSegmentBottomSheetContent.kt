package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.czinkem.nessaj_twitch_schedule.domain.AppProvider
import com.github.czinkem.nessaj_twitch_schedule.domain.DateHelper
import com.github.czinkem.nessaj_twitch_schedule.domain.NotificationManager
import com.github.czinkem.nessaj_twitch_schedule.twitchPurple
import com.github.czinkem.nessaj_twitch_schedule.youtubeRed
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import nessajtwitchschedule.composeapp.generated.resources.Res
import nessajtwitchschedule.composeapp.generated.resources.icon_twitch
import nessajtwitchschedule.composeapp.generated.resources.icon_youtube
import org.jetbrains.compose.resources.painterResource
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
    closeSheet: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val appLauncher = koinInject<AppProvider>()
    val notificationManager = koinInject<NotificationManager>()
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) {
        factory.createPermissionsController()
    }
    BindEffect(controller)

    var permissionState by remember {
        mutableStateOf(PermissionState.NotDetermined)
    }
    var shouldShowDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        permissionState = controller.getPermissionState(Permission.REMOTE_NOTIFICATION)
    }

    fun provideOrRequestNotificationPermission() {
        scope.launch {
            try {
                controller.providePermission(Permission.REMOTE_NOTIFICATION)
                permissionState = PermissionState.Granted
            } catch(e: DeniedAlwaysException) {
                permissionState = PermissionState.DeniedAlways
            } catch(e: DeniedException) {
                permissionState = PermissionState.Denied
            } catch(e: RequestCanceledException) {
                e.printStackTrace()
            }
        }
    }

    if(shouldShowDialog) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog = false
            },
            modifier = Modifier,
            title = {
                Text(text = "Notification permission")
            },
            text = {
                if(permissionState == PermissionState.DeniedAlways) {
                    Text(text = "The app needs notification permission in order to set stream reminders, but it has been denied. Go to app settings to give permissions!")
                } else {
                    Text(text = "The app needs notification permission in order to set and send stream reminder notifications!")
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        onClick = {
                            shouldShowDialog = false
                        }
                    ) {
                        Text(text = "Cancel")
                    }
                    if(permissionState == PermissionState.DeniedAlways) {
                        Button(
                            onClick = {
                                controller.openAppSettings()
                                shouldShowDialog = false
                            }
                        ) {
                            Text(text = "Go to Settings")
                        }
                    } else {
                        Button(
                            onClick = {
                                provideOrRequestNotificationPermission()
                                shouldShowDialog = false
                            }
                        ) {
                            Text(text = "Give Permissions")
                        }
                    }
                }
            },
            shape = MaterialTheme.shapes.large
        )
    }

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
        when {
            DateHelper.isTimePassed(state.time) -> {
                Button(
                    onClick = {
                        appLauncher.openYoutubeApp()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = youtubeRed,
                        contentColor = MaterialTheme.colors.onPrimary
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.icon_youtube),
                        contentDescription = null
                    )
                    Spacer(
                        modifier = Modifier.size(4.dp)
                    )
                    Text(
                        text = "Go to VOD channel"
                    )
                }
            }
            state.isLive -> {
                Button(
                    onClick = {
                        appLauncher.openTwitchApp()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = twitchPurple,
                        contentColor = MaterialTheme.colors.onPrimary
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.icon_twitch),
                        contentDescription = null
                    )
                    Spacer(
                        modifier = Modifier.size(4.dp)
                    )
                    Text(
                        text = "Open Twitch stream"
                    )
                }
            }
            else -> {
                OutlinedButton(
                    border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                    onClick = {
                        if(permissionState == PermissionState.Granted) {
                            notificationManager.setNotificationAtTime(
                                time = state.time,
                                notificationTitle = state.title,
                                notificationText = "Fyrexxx's ${state.title} stream is starting!"
                            )
                            closeSheet()
                        }else {
                            shouldShowDialog = true
                        }
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
}