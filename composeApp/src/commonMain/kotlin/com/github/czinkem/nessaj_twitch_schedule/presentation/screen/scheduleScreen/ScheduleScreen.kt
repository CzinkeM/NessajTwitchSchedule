package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        val isScheduleLoading by screenModel.isScheduleLoading.collectAsStateWithLifecycle()

        Surface(
            color = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier.background(color = MaterialTheme.colors.surface)
            ) {
                TopAppBar(
                    title = {
                        Icon(
                            imageVector = Icons.Default.Android,
                            contentDescription = null
                        )
                    }
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(MaterialTheme.colors.surface),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        WeekSelector(
                            modifier = Modifier.fillMaxWidth(),
                            startOfWeek = state.startOfWeek,
                            endOfWeek = state.endOfWeek,
                            onPreviousButtonClick = {
                                screenModel.onPreviousWeekButtonClicked()
                            },
                            onNextButtonClick = {
                                screenModel.onNextWeekButtonClicked()
                            }
                        )
                    }
                    if(isScheduleLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colors.surface)
                                    .padding(top = 32.dp)
                            ) {
                                Column(
                                    modifier = Modifier.align(Alignment.Center),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator()
                                    Text(text = "Loading selected week data!")
                                }
                            }
                        }
                    }else {
                        if(state.segments.isEmpty()) {
                            item {
                                NothingPlannedCard(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 32.dp)
                                )
                            }
                        }else {
                            state.segments.forEach { (day, segments)->
                                item {
                                    DayOfWeekHeader(
                                        modifier = Modifier.padding(vertical = 16.dp),
                                        dayOfWeek = day
                                    )
                                }
                                items(segments) { segment ->
                                    SegmentCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(200.dp, 300.dp)
                                            .padding(vertical = 8.dp),
                                        state = segment
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}