package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegmentCategoryType
import kotlinx.datetime.LocalDateTime

data class SegmentCardState(
    val title: String,
    val artUrl: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val categoryName: String,
    val category: TwitchScheduleSegmentCategoryType
)
