package com.github.czinkem.nessaj_twitch_schedule.domain

import kotlinx.datetime.LocalDateTime

expect class NotificationManager {
    fun setNotificationAtTime(time: LocalDateTime)
}