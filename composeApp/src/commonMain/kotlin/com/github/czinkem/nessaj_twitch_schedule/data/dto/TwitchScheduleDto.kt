package com.github.czinkem.nessaj_twitch_schedule.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TwitchScheduleDto(
    val data: TwitchScheduleDataDto,
)

@Serializable
data class TwitchScheduleDataDto(
    @SerialName("segments")
    val segments: List<TwitchScheduleSegmentDto>? = null,
    @SerialName("broadcaster_id")
    val broadcasterId: String = "",
    @SerialName("broadcaster_name")
    val broadcasterName: String = "",
    @SerialName("vacation")
    val vacation: String? = null
)

@Serializable
data class TwitchScheduleSegmentDto(
    @SerialName("id")
    val id: String = "",
    @SerialName("start_time")
    val startTime: String = "",
    @SerialName("end_time")
    val endTime: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("category")
    val category: TwitchSegmentCategoryDto? = null,
)

@Serializable
data class TwitchSegmentCategoryDto(
    @SerialName("id")
    val id: String = "",
    @SerialName("name")
    val name: String = "",
)