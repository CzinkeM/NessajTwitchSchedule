package com.github.czinkem.nessaj_twitch_schedule.di

import com.github.czinkem.nessaj_twitch_schedule.data.TwitchScheduleRepository
import com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen.ScheduleScreenModel
import org.koin.dsl.module

val commonModule = module {
    single { TwitchScheduleRepository(get()) }
    factory { ScheduleScreenModel(get()) }
}