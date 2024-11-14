package com.github.czinkem.nessaj_twitch_schedule.domain

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri


actual class AppProvider(
    private val context: Context,
) {
    actual fun openTwitchApp() {
        try {
            context.startActivity(IntentProvider.Twitch.app)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(IntentProvider.Twitch.web)
        }
    }

    actual fun openYoutubeApp() {
        val youtubeURL = "https://www.youtube.com/@nessajgaming"

        try {
            val youtubeIntent = Intent(Intent.ACTION_VIEW).apply {
                setPackage("com.google.android.youtube")
                setData(Uri.parse(youtubeURL))
                addFlags(FLAG_ACTIVITY_NEW_TASK)

            }
            context.startActivity(youtubeIntent)
        } catch (e: ActivityNotFoundException) {
            val youtubeIntent = Intent(Intent.ACTION_VIEW).apply {
                setData(Uri.parse(youtubeURL))
                addFlags(FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(youtubeIntent)
        }
    }
}