package com.github.czinkem.nessaj_twitch_schedule.data

import com.github.czinkem.nessaj_twitch_schedule.data.dto.TwitchGameDto
import com.github.czinkem.nessaj_twitch_schedule.data.dto.TwitchScheduleDto
import com.github.czinkem.nessaj_twitch_schedule.data.dto.TwitchUsersDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import kotlinx.serialization.json.Json

class TwitchHttpClient(
    private val client: HttpClient
) {
    private object HeaderKeys {
        const val CLIENT_ID = "Client-Id"
    }
    private object ParameterKeys {
        const val BROADCASTER_ID = "broadcaster_id"
        const val LOGIN = "login"
        const val ID = "id"
    }

    suspend fun getChannelInfo(clientId: String): TwitchUsersDto {
        val result = try {
            client.get(urlString = TwitchUrlProvider.USERS_URL) {

                headers {
                    this[HeaderKeys.CLIENT_ID] = clientId
                }
                parameter(ParameterKeys.LOGIN, "fyrexxx")
            }
        } catch (e: Exception) {
            throw e
        }

        return when (result.status.value) {
            in 200..299 -> {
                Json{ignoreUnknownKeys = true}.decodeFromString<TwitchUsersDto>(result.body<String>())
            }
            else -> {
                throw ResponseException(result, "")
            }
        }
    }

    suspend fun getGamesInfo(clientId: String, gameId: String): TwitchGameDto {
        val result = try {
            client.get(urlString = TwitchUrlProvider.GAMES_URL) {

                headers {
                    this[HeaderKeys.CLIENT_ID] = clientId
                }
                parameter(ParameterKeys.ID, gameId)
            }
        } catch (e: Exception) {
            throw e
        }

        return when (result.status.value) {
            in 200..299 -> {
                Json{ignoreUnknownKeys = true}.decodeFromString<TwitchGameDto>(result.body<String>())
            }
            else -> {
                throw ResponseException(result, "")
            }
        }
    }

    suspend fun getBroadcasterSchedule(clientId: String, broadcasterId: String): TwitchScheduleDto {
        val result = try {
            client.get(urlString = TwitchUrlProvider.SCHEDULE_URL) {

                headers {
                    this[HeaderKeys.CLIENT_ID] = clientId
                }
                parameter(ParameterKeys.BROADCASTER_ID, broadcasterId)
            }
        } catch (e: Exception) {
            throw e
        }

        return when (result.status.value) {
            in 200..299 -> {
                Json{ignoreUnknownKeys = true}.decodeFromString<TwitchScheduleDto>(result.body<String>())
            }
            else -> {
                throw ResponseException(result, "")
            }
        }
    }
}