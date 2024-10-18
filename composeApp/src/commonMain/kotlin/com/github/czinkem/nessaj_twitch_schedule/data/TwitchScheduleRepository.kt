package com.github.czinkem.nessaj_twitch_schedule.data

import androidx.compose.ui.util.fastMapNotNull
import com.github.czinkem.nessaj_twitch_schedule.data.dto.TwitchGameDto
import com.github.czinkem.nessaj_twitch_schedule.domain.mapper.toTwitchSchedule
import com.github.czinkem.nessaj_twitch_schedule.domain.model.TwitchSchedule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TwitchScheduleRepository(
    private val httpClient: TwitchHttpClient,
) {
    suspend fun getScheduleTest(): TwitchSchedule {
        return httpClient.getBroadcasterSchedule(
            clientId = getClientId(),
            broadcasterId = "40261250",
            dateString = "2024-10-14T09:00:19Z"
        ).toTwitchSchedule()
    }

    fun getSchedule(): Flow<TwitchSchedule> {
        return flow {
            val scheduleDto = httpClient.getBroadcasterSchedule(
                clientId = getClientId(),
                broadcasterId = "40261250",
                dateString = "2024-10-14T09:00:19Z"
            )
            if (scheduleDto.data.segments != null){
                val gameInfos = getGamesInfo(scheduleDto.data.segments?.fastMapNotNull { it.category?.id ?: "" } ?: emptyList())

            }else {
                scheduleDto.toTwitchSchedule()
            }
        }
    }

    suspend fun getChannelInfo(): String {
        return httpClient.getChannelInfo(clientId = getClientId()).toString()
    }

    suspend fun getGameInfo(gameId: String): TwitchGameDto {
        return httpClient.getGamesInfo(clientId = getClientId(), gameId = gameId)
    }
    suspend fun getGamesInfo(gameIdList: List<String>): List<TwitchGameDto> {
        return gameIdList.map { id -> getGameInfo(id) }
    }
}