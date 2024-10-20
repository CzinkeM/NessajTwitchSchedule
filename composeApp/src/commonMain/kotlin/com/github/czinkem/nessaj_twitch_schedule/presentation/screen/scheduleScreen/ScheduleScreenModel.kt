package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.github.czinkem.nessaj_twitch_schedule.data.TwitchScheduleRepository
import com.github.czinkem.nessaj_twitch_schedule.domain.DateHelper
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchSchedule
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ScheduleScreenModel(
    private val repository: TwitchScheduleRepository
): ScreenModel {

    private val selectedWeek = MutableStateFlow(DateHelper.Week.getCurrentWeek())
    val isScheduleLoading = MutableStateFlow(true)
    private val selectedSchedule = selectedWeek.map {
        isScheduleLoading.update { true }
        val schedule = repository.getSchedule(it.first, it.second)
        isScheduleLoading.update { false }
        schedule
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(3000), TwitchSchedule(emptyList(),null))

    val state = combine(selectedWeek, selectedSchedule) {  week, schedule ->
        ScheduleScreenState(
            segments = schedule.segments.map { it.toSegmentCardState() },
            startOfWeek = week.first.toString(),
            endOfWeek = week.second.toString()
        )
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(3000L), ScheduleScreenState(emptyList(),"",""))

    fun onPreviousWeekButtonClicked() {
        selectedWeek.update {
            DateHelper.Week.getPreviousWeek(startOfWeek = it.first)
        }
    }

    fun onNextWeekButtonClicked() {
        selectedWeek.update {
            DateHelper.Week.getNextWeek(endOfWeek = it.second)
        }
    }


    private fun TwitchScheduleSegment.toSegmentCardState(): SegmentCardState {
        return SegmentCardState(
            title = this.title,
            artUrl = this.category!!.artUrl, // TODO: if it is null or blank use default art
            timeString = "${this.startTime}",
            categoryName = this.category.name,  // TODO: if it is null or blank use default art
            category = this.category.type,
        )
    }
}