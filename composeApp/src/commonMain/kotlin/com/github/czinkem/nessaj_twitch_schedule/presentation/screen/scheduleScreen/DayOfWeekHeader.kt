package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.DayOfWeek

@Composable
fun DayOfWeekHeader(
    modifier: Modifier = Modifier,
    dayOfWeek: DayOfWeek,
    selected: Boolean,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if(selected) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Label,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
            Spacer(
                modifier = Modifier.size(8.dp)
            )
        }
        Text(
            modifier = Modifier,
            text = dayOfWeek.toLocalizedString(),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            maxLines = 1,
            color =  if(selected) {
                MaterialTheme.colors.primary
            }else {
                MaterialTheme.colors.onSurface
            }
        )
    }
}

fun DayOfWeek.toLocalizedString(): String {
    return when (this) {
        DayOfWeek.MONDAY -> "Hétfő"
        DayOfWeek.TUESDAY -> "Kedd"
        DayOfWeek.WEDNESDAY -> "Szerda"
        DayOfWeek.THURSDAY -> "Csütörtök"
        DayOfWeek.FRIDAY -> "Péntek"
        DayOfWeek.SATURDAY -> "Szombat"
        DayOfWeek.SUNDAY -> "Vasárnap"
        else -> throw IllegalArgumentException("$this is not a DayOfWeek")
    }
}