package com.github.czinkem.nessaj_twitch_schedule.domain

import android.content.Context
import android.widget.Toast
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.github.czinkem.nessaj_twitch_schedule.NotificationWorker
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.util.concurrent.TimeUnit

actual class NotificationManager(
    private val context: Context,
) {
    actual fun setNotificationAtTime(time: LocalDateTime, notificationTitle: String, notificationText: String) {
        val delayInMs = time.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds() - System.currentTimeMillis()
        val notificationData = workDataOf(
            NotificationWorker.NOTIFICATION_TITLE to notificationTitle,
            NotificationWorker.NOTIFICATION_DESCRIPTION to notificationText
        )
        Toast.makeText(context, "Notification will appear in 30s", Toast.LENGTH_LONG).show()
        val notificationWork = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(notificationData)
            .setInitialDelay(delayInMs, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueue(notificationWork)
    }
}