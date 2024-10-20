package com.github.czinkem.nessaj_twitch_schedule.data

import com.github.czinkem.nessaj_twitch_schedule.data.dto.TwitchGameDto
import com.github.czinkem.nessaj_twitch_schedule.data.dto.TwitchScheduleDto
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchSchedule
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegment
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegmentCategory
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegmentCategoryType
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime

class TwitchScheduleRepository(
    private val httpClient: TwitchHttpClient,
) {

    suspend fun getSchedule(startDate: LocalDate, endDate: LocalDate): TwitchSchedule {
        val endLocalDateTime = endDate.atTime(0,0,0)
        val startDateString = startDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toString()
        var shouldGetMoreSegment = true
        lateinit var twitchSchedule: TwitchSchedule
        val twitchScheduleSegments = mutableListOf<TwitchScheduleSegment>()
        while (shouldGetMoreSegment) {
            val tempTwitchSchedule  = httpClient.getBroadcasterSchedule(
                clientId = getClientId(),
                broadcasterId = "40261250",
                startDate = startDateString,
            ).mapDtoToModel()

            twitchSchedule = tempTwitchSchedule

            if(tempTwitchSchedule.segments.all { it.startTime < endLocalDateTime }) {
                twitchScheduleSegments.addAll(twitchSchedule.segments)
                break
            }

            if(tempTwitchSchedule.segments.any { it.startTime > endLocalDateTime }) {
                twitchScheduleSegments.addAll(twitchSchedule.segments.filter { it.startTime < endLocalDateTime })
                break
            }

            twitchScheduleSegments.addAll(twitchSchedule.segments)

        }

        return twitchSchedule.copy(segments = twitchScheduleSegments)
    }
    //TODO: reserved for further use
    suspend fun getChannelInfo(): String {
        return httpClient.getChannelInfo(clientId = getClientId()).toString()
    }

    private suspend fun getGameInfo(gameId: String): TwitchGameDto {
        return httpClient.getGamesInfo(clientId = getClientId(), gameId = gameId)
    }

    private suspend fun TwitchScheduleDto.mapDtoToModel(): TwitchSchedule {
        val segments = this.data.segments?.map { dto ->
            TwitchScheduleSegment(
                id = dto.id,
                startTime = Instant.parse(dto.startTime).toLocalDateTime(TimeZone.currentSystemDefault()),
                endTime = Instant.parse(dto.endTime).toLocalDateTime(TimeZone.currentSystemDefault()),
                title = dto.title,
                category = getTwitchScheduleSegmentCategory(dto.category?.id)
            )
        }
        return TwitchSchedule(
            segments = segments ?: emptyList(),
            vacation = this.data.vacation
        )
    }

    private suspend fun getTwitchScheduleSegmentCategory(categoryId: String?): TwitchScheduleSegmentCategory? {
        if(categoryId == null)
            return null
        val dto = getGameInfo(categoryId).data.firstOrNull() ?: return null
        return TwitchScheduleSegmentCategory(
            id = dto.id,
            name = dto.name,
            artUrl = dto.artUrl,
            type = TwitchScheduleSegmentCategoryType.parse(dto.name)
        )
    }
}