package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.github.czinkem.nessaj_twitch_schedule.data.TwitchScheduleRepository
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchSchedule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScheduleScreenModel(
    private val repository: TwitchScheduleRepository
): ScreenModel {


    private val selectedSchedule = MutableStateFlow(TwitchSchedule(emptyList(), null))
    private val selectedWeek = MutableStateFlow("")

    val state = selectedSchedule.map { schedule ->
        ScheduleScreenState(
            segments = schedule.segments.map { it.title }
        )
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(3000L), ScheduleScreenState(emptyList()))

    fun getData() {
        getSchedule()
    }

    private fun getSchedule() {
        screenModelScope.launch {
            selectedSchedule.update {
                repository.getScheduleTest()
            }
        }
    }
}