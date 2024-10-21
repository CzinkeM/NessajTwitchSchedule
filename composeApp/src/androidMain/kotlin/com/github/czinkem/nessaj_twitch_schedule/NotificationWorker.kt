package com.github.czinkem.nessaj_twitch_schedule

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {
    //FIXME: add request for permission when clicked the set notification
    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        val title = inputData.getString(NOTIFICATION_TITLE) ?: "Fyrexxx Stream Starts"
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
            .setSmallIcon(R.drawable.ic_launcher_foreground)
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