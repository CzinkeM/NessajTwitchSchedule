package com.github.czinkem.nessaj_twitch_schedule

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform