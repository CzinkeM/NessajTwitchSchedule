package com.github.czinkem.nessaj_twitch_schedule.domain

import android.content.Context
import android.widget.Toast
import kotlinx.datetime.LocalDateTime

actual class NotificationManager(
    private val context: Context,
) {
    actual fun setNotificationAtTime(time: LocalDateTime) {
        Toast.makeText(context, "Currently Notifications not supported", Toast.LENGTH_LONG).show()
    }
}