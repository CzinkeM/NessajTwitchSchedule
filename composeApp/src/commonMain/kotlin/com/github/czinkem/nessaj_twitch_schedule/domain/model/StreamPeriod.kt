package com.github.czinkem.nessaj_twitch_schedule.domain.model

data class StreamPeriod(
    val currentWeek: StreamWeek,
    val minusOneWeek: StreamWeek,
    val minusTwoWeek: StreamWeek,
    val plusOneWeek: StreamWeek,
    val plusTwoWeek: StreamWeek,
)