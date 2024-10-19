package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.github.czinkem.nessaj_twitch_schedule.data.TwitchScheduleRepository
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchSchedule
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class ScheduleScreenModel(
    private val repository: TwitchScheduleRepository
): ScreenModel {

    private val selectedWeek = MutableStateFlow(Clock.System.now().toString())
    private val selectedSchedule = selectedWeek.map {
        repository.getSchedule(it)
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(3000), TwitchSchedule(emptyList(),null))

    val state = combine(selectedWeek, selectedSchedule) {  week, schedule ->
        ScheduleScreenState(
            segments = schedule.segments?.map { it.toSegmentCardState() } ?: emptyList(),
            startOfWeek = "10.13",
            endOfWeek = "10.20"
        )
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(3000L), ScheduleScreenState(emptyList(),"",""))

    init {
        getData()
    }

    private fun getData() {
        getSchedule()
    }

    private fun getSchedule() {
        screenModelScope.launch {
            selectedWeek.update {
                "2024-10-14T09:00:19Z"
            }
        }
    }

    private fun TwitchScheduleSegment.toSegmentCardState(): SegmentCardState {
        return SegmentCardState(
            title = this.title,
            artUrl = this.category!!.artUrl, // TODO: if it is null or blank use default art
            timeString = "${this.startTime.hour}-${this.endTime.hour}",
            categoryName = this.category.name,  // TODO: if it is null or blank use default art
            category = this.category.type,
        )
    }
}