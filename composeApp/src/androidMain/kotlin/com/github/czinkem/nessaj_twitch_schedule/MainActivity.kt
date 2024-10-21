package com.github.czinkem.nessaj_twitch_schedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.czinkem.nessaj_twitch_schedule.di.commonModule
import com.github.czinkem.nessaj_twitch_schedule.domain.AppProvider
import com.github.czinkem.nessaj_twitch_schedule.domain.NotificationManager
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            modules(
                commonModule,
                androidModule,
                module {
                    single { AppProvider(this@MainActivity) }
                    single { NotificationManager(this@MainActivity) }
                }
            )
        }
        setContent {
            App()
        }
    }

    override fun onStop() {
        super.onStop()
        stopKoin()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}