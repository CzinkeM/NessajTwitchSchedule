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
import kotlinx.datetime.DayOfWeek

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
            segments = getScheduleSegmentMap(schedule.segments),
            startOfWeek = "${week.first.monthNumber}.${week.first.dayOfMonth}.",
            endOfWeek = "${week.second.monthNumber}.${week.second.dayOfMonth}."
        )
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(3000L), ScheduleScreenState( emptyMap(),"",""))

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
            startTime = this.startTime, //"${this.startTime.hour}:${this.startTime.minute} - ${this.endTime.hour}:${this.endTime.minute}",
            endTime = this.endTime,
            categoryName = this.category.name,  // TODO: if it is null or blank use default art
            category = this.category.type,
        )
    }

    private fun getScheduleSegmentMap(segments: List<TwitchScheduleSegment>): Map<DayOfWeek, List<SegmentCardState>> {
        return segments
            .groupBy { segment -> segment.startTime.dayOfWeek}
            .map{ (day, segments) -> day to segments.map { it.toSegmentCardState() } }
            .toMap()
    }
}