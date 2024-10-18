package com.github.czinkem.nessaj_twitch_schedule

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen.ScheduleScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(ScheduleScreen())
    }
}