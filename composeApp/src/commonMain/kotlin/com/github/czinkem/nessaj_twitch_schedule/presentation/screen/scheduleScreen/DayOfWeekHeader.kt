package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.datetime.DayOfWeek

@Composable
fun DayOfWeekHeader(
    modifier: Modifier = Modifier,
    dayOfWeek: DayOfWeek,
) {
    Text(
        modifier = modifier,
        text = dayOfWeek.toLocalizedString(),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        maxLines = 1,
    )
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