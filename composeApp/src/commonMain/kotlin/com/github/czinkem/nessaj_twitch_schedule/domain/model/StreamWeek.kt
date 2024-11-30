package com.github.czinkem.nessaj_twitch_schedule.domain.model

import kotlinx.datetime.LocalDate

data class StreamWeek (
    val week: Pair<LocalDate, LocalDate>,
    val schedule: TwitchSchedule
)
