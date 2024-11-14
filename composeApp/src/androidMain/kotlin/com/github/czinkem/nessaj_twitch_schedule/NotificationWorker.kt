package com.github.czinkem.nessaj_twitch_schedule

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.github.czinkem.nessaj_twitch_schedule.domain.IntentProvider

class NotificationWorker(private val appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        val title = "Fyrexxx's Stream Starts" //inputData.getString(NOTIFICATION_TITLE)
        val description = inputData.getString(NOTIFICATION_DESCRIPTION) ?: "Fyrexxx Stream Starts"
        val notification = createNotification(title, description)
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NOTIFICATION_ID, notification.build())
        }
        return Result.success()
    }

    private fun createNotification(title: String, description: String): NotificationCompat.Builder {
        createNotificationChannel()
        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(description)
            .setColor(penguinOrange.toArgb())
            .setContentIntent(
                TaskStackBuilder.create(appContext).run {
                    addNextIntentWithParentStack(IntentProvider.Twitch.web)
                    getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                }
            )
            .setSmallIcon(R.drawable.icon_notification)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object{
        const val CHANNEL_ID = "channel_id"
        const val NOTIFICATION_ID = 1

        const val NOTIFICATION_TITLE = "title"
        const val NOTIFICATION_DESCRIPTION = "description"
    }
}