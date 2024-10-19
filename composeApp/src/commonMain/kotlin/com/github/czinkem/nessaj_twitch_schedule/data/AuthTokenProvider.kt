package com.github.czinkem.nessaj_twitch_schedule.data

expect fun getAuthToken(): String

expect fun saveAuthToken(token: String)