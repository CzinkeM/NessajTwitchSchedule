package com.github.czinkem.nessaj_twitch_schedule.data

import com.github.czinkem.nessaj_twitch_schedule.BuildConfig

actual fun getClientId(): String {
    return BuildConfig.CLIENT_ID
}