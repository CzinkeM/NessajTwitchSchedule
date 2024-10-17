package com.github.czinkem.nessaj_twitch_schedule

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}