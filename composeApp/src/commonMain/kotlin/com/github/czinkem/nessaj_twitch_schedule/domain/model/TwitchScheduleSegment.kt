package com.github.czinkem.nessaj_twitch_schedule.domain.model

import kotlinx.datetime.LocalDateTime

data class TwitchScheduleSegment(
    val id: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val title: String,
    val category: TwitchScheduleSegmentCategory?,
)
