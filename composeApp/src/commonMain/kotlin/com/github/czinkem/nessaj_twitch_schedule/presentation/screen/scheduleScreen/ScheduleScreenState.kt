package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import kotlinx.datetime.DayOfWeek

data class ScheduleScreenState(
    val segments: Map<DayOfWeek, List<SegmentCardState>>,
    val startOfWeek: String,
    val endOfWeek: String
)