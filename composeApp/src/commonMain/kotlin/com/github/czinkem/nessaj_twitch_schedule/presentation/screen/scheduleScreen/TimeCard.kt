package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimeCard(modifier: Modifier = Modifier, timeString: String) {
    Card(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Schedule,
                contentDescription = null
            )
            Text(
                text = timeString
            )
        }
    }
}