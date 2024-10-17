package com.github.czinkem.nessaj_twitch_schedule.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TwitchUsersDto(
    @SerialName("data")
    val data: List<TwitchUserDto>
)

@Serializable
data class TwitchUserDto(
    @SerialName("id")
    val id: String,
    @SerialName("display_name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("profile_image_url")
    val profileImageUrl: String,
)