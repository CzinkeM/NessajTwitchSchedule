package com.github.czinkem.nessaj_twitch_schedule

import android.app.Application
import com.github.czinkem.nessaj_twitch_schedule.di.commonModule
import com.github.czinkem.nessaj_twitch_schedule.domain.AppProvider
import com.github.czinkem.nessaj_twitch_schedule.domain.NotificationManager
import org.koin.core.context.startKoin
import org.koin.dsl.module

class NessajScheduleApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                commonModule,
                androidModule,
                module {
                    single { AppProvider(this@NessajScheduleApp) }
                    single { NotificationManager(this@NessajScheduleApp) }
                }
            )
        }
    }
}