package com.github.czinkem.nessaj_twitch_schedule.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TwitchGameDto(
    @SerialName("data")
    val data: List<TwitchGameInfoDto>
)

@Serializable
data class TwitchGameInfoDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("box_art_url")
    val artUrl: String,
)