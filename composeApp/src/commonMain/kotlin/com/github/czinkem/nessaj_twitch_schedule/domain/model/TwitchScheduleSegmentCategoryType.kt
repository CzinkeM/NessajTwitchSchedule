package com.github.czinkem.nessaj_twitch_schedule.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.github.czinkem.nessaj_twitch_schedule.fifaStreamGreen
import com.github.czinkem.nessaj_twitch_schedule.penguinOrange
import com.github.czinkem.nessaj_twitch_schedule.reactionStreamRed


enum class TwitchScheduleSegmentCategoryType {
    JustChatting,
    Fifa,
    OtherGame;

    companion object {
        private val fifaCategoryRegex = Regex("(fifa|fc)")

        fun parse(categoryName: String): TwitchScheduleSegmentCategoryType {
            with(categoryName.toLowerCase(Locale.current)) {
                return when {
                    this.contains("just chatting", true) -> JustChatting
                    this.contains(fifaCategoryRegex) -> Fifa
                    else -> OtherGame
                }
            }
        }
    }

    fun color(): Color {
        return when(this) {
            JustChatting -> reactionStreamRed
            Fifa -> fifaStreamGreen
            OtherGame -> penguinOrange
        }
    }
}