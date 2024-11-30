package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.github.czinkem.nessaj_twitch_schedule.data.TwitchScheduleRepository
import com.github.czinkem.nessaj_twitch_schedule.domain.DateHelper
import com.github.czinkem.nessaj_twitch_schedule.domain.model.StreamPeriod
import com.github.czinkem.nessaj_twitch_schedule.domain.model.StreamWeek
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.datetime.DayOfWeek

class ScheduleScreenModel(
    private val repository: TwitchScheduleRepository
): ScreenModel {


    private val currentWeek = MutableStateFlow(DateHelper.Week.getCurrentWeek())

    private val streamPeriod = currentWeek.map { currentWeek ->
        val minusOneWeek = DateHelper.Week.getPreviousWeek(currentWeek.first)
        val minusTwoWeek = DateHelper.Week.getPreviousWeek(minusOneWeek.first)
        val plusOneWeek = DateHelper.Week.getNextWeek(currentWeek.second)
        val plusTwoWeek = DateHelper.Week.getNextWeek(plusOneWeek.second)
        isScheduleLoading.update { true }
        val period = StreamPeriod(
            currentWeek = StreamWeek(currentWeek, repository.getSchedule(currentWeek.first, currentWeek.second)),
            minusOneWeek = StreamWeek(minusOneWeek, repository.getSchedule(minusOneWeek.first, minusOneWeek.second)),
            minusTwoWeek = StreamWeek(minusTwoWeek, repository.getSchedule(minusTwoWeek.first, minusTwoWeek.second)),
            plusOneWeek = StreamWeek(plusOneWeek, repository.getSchedule(plusOneWeek.first, plusOneWeek.second)),
            plusTwoWeek = StreamWeek(plusTwoWeek, repository.getSchedule(plusTwoWeek.first, plusTwoWeek.second)),
        )
        isScheduleLoading.update { false }
        period
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(3000), null)

    val isScheduleLoading = MutableStateFlow(true)

    private fun StreamWeek.toState(): StreamWeekState {
        return StreamWeekState(
            segments = getScheduleSegmentMap(schedule.segments),
            startOfWeek = "${week.first.monthNumber}.${week.first.dayOfMonth}.",
            endOfWeek = "${week.second.monthNumber}.${week.second.dayOfMonth}."
        )
    }

    val state = streamPeriod.map { period ->
        return@map if(period == null) {
            ScheduleScreenState.EMPTY
        }
        else {
            ScheduleScreenState(
                currentWeek =  period.currentWeek.toState(),
                minusOneWeek = period.minusOneWeek.toState(),
                minusTwoWeek = period.minusTwoWeek.toState(),
                plusOneWeek = period.plusOneWeek.toState(),
                plusTwoWeek = period.plusTwoWeek.toState(),
            )
        }
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(3000L), ScheduleScreenState.EMPTY)

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