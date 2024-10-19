package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

data class ScheduleScreenState(
    val segments: List<SegmentCardState>,
    val startOfWeek: String,
    val endOfWeek: String
)