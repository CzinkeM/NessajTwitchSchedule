package com.github.czinkem.nessaj_twitch_schedule.domain

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri

object IntentProvider {
    object Twitch {
        val web = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitch.tv/fyrexxx")).apply {
            addFlags(FLAG_ACTIVITY_NEW_TASK)
        }
        val app = Intent(Intent.ACTION_VIEW, Uri.parse("twitch://stream/fyrexxx")).apply{
            addFlags(FLAG_ACTIVITY_NEW_TASK)
        }
    }
}