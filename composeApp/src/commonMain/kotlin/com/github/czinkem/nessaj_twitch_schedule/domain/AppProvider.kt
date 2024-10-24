package com.github.czinkem.nessaj_twitch_schedule.domain

expect class AppProvider {
    fun openTwitchApp()

    fun openYoutubeApp()
}