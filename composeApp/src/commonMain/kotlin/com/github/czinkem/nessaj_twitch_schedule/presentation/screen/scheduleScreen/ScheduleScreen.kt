package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class ScheduleScreen: Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.getNavigatorScreenModel<ScheduleScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        Column {
            TopAppBar(
                title = {
                    Icon(
                        imageVector = Icons.Default.Android,
                        contentDescription = null
                    )
                }
            )
            LazyColumn {
                item {
                    Button(
                        onClick = {
                            screenModel.getData()
                        }
                    ) {
                        Text(
                            text = "Get Data"
                        )
                    }
                }
                items(state.segments) { segment ->
                    Text(text = segment)
                }
            }
        }
    }
}