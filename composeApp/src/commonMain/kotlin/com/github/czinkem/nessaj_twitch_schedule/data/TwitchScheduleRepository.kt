package com.github.czinkem.nessaj_twitch_schedule.data

import com.github.czinkem.nessaj_twitch_schedule.data.dto.TwitchGameDto
import com.github.czinkem.nessaj_twitch_schedule.data.dto.TwitchScheduleDto
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchSchedule
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegment
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegmentCategory
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchScheduleSegmentCategoryType
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TwitchScheduleRepository(
    private val httpClient: TwitchHttpClient,
) {

    suspend fun getSchedule(dateString: String): TwitchSchedule {
        return httpClient.getBroadcasterSchedule(
            clientId = getClientId(),
            broadcasterId = "40261250",
            dateString = dateString
        ).mapDtoToModel()
    }

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
            segments = segments,
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