package com.github.czinkem.nessaj_twitch_schedule.domain

import android.content.Context
import android.widget.Toast
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.github.czinkem.nessaj_twitch_schedule.NotificationWorker
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.util.concurrent.TimeUnit

actual class NotificationManager(
    private val context: Context,
) {
    actual fun setNotificationAtTime(time: LocalDateTime, notificationTitle: String, notificationText: String) {
        val startTime = time.toInstant(TimeZone.currentSystemDefault())
        val delay = (startTime - Clock.System.now()).inWholeMinutes
        val notificationData = workDataOf(
            NotificationWorker.NOTIFICATION_TITLE to notificationTitle,
            NotificationWorker.NOTIFICATION_DESCRIPTION to notificationText
        )
        val notificationWork = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(notificationData)
            .setInitialDelay(delay, TimeUnit.MINUTES)
            // FOR TESTING PURPOSES
//            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueue(notificationWork)
        Toast.makeText(context, "Notification set for $notificationTitle", Toast.LENGTH_SHORT).show()
    }
}