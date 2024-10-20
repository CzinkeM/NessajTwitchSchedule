package com.github.czinkem.nessaj_twitch_schedule.domain.model

data class TwitchSchedule(
    val segments: List<TwitchScheduleSegment>,
    val vacation: String?
)
