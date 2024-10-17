package com.github.czinkem.nessaj_twitch_schedule

import com.github.czinkem.nessaj_twitch_schedule.data.TwitchHttpClient
import com.github.czinkem.nessaj_twitch_schedule.data.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module

val androidModule = module {
    single { TwitchHttpClient(createHttpClient(OkHttp.create())) }
}