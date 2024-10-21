package com.github.czinkem.nessaj_twitch_schedule.domain

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

actual class AppProvider(
    private val context: Context,
) {
    actual fun openTwitchApp() {
        val twitchAppUri = Uri.parse("twitch://stream/fyrexxx")
        val twitchWebUri = Uri.parse("https://www.twitch.tv/fyrexxx")

        val twitchAppIntent = Intent(Intent.ACTION_VIEW, twitchAppUri)
        twitchAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            context.startActivity(twitchAppIntent)
        } catch (e: ActivityNotFoundException) {
            val webIntent = Intent(Intent.ACTION_VIEW, twitchWebUri)
            context.startActivity(webIntent)
        }
    }
}