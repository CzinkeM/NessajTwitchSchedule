package com.github.czinkem.nessaj_twitch_schedule.data

import com.github.czinkem.nessaj_twitch_schedule.BuildConfig

actual fun getAuthToken(): String {
    return BuildConfig.AUTH_TOKEN
}

actual fun saveAuthToken(token: String) {
    TODO("The token should be saved to the device and stored securely after login")
}