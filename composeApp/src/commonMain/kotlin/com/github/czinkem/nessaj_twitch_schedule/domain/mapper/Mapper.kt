package com.github.czinkem.nessaj_twitch_schedule.domain.mapper

import com.github.czinkem.nessaj_twitch_schedule.data.dto.TwitchScheduleDto
import com.github.czinkem.nessaj_twitch_schedule.data.dto.TwitchScheduleSegmentDto
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchSchedule
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegment
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegmentCategory
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun TwitchScheduleDto.toTwitchSchedule(): TwitchSchedule {
    return TwitchSchedule(
        segments = this.data.segments?.map { it.toTwitchScheduleSegment() } ?: emptyList(),
        vacation = this.data.vacation
    )
}

fun TwitchScheduleSegmentDto.toTwitchScheduleSegment(artUrl: String = ""): TwitchScheduleSegment {
// FIXME: Handle missing data correctly
    return TwitchScheduleSegment(
        id = this.id,
        startTime = Instant.parse(this.startTime).toLocalDateTime(TimeZone.UTC),
        endTime = Instant.parse(this.endTime).toLocalDateTime(TimeZone.UTC),
        title = this.title,
        category = TwitchScheduleSegmentCategory(this.category?.id ?: "", this.category?.name ?: "", artUrl)
    )
}