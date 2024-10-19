package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

        Surface {
            Column {
                TopAppBar(
                    title = {
                        Icon(
                            imageVector = Icons.Default.Android,
                            contentDescription = null
                        )
                    }
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        WeekSelector(
                            modifier = Modifier.fillMaxWidth(),
                            startOfWeek = state.startOfWeek,
                            endOfWeek = state.endOfWeek,
                            onPreviousButtonClick = {},
                            onNextButtonClick = {}
                        )
                    }
                    items(items  = state.segments) { segmentCardState ->
                        SegmentCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(vertical = 8.dp)
                            ,
                            state = segmentCardState
                        )
                    }

                }
            }
        }
    }
}