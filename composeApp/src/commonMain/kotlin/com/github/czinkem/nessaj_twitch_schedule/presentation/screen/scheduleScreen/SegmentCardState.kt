package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegmentCategoryType

data class SegmentCardState(
    val title: String,
    val artUrl: String,
    val timeString: String,
    val categoryName: String,
    val category: TwitchScheduleSegmentCategoryType
)
