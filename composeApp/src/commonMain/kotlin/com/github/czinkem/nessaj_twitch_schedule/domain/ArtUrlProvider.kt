package com.github.czinkem.nessaj_twitch_schedule.domain

import androidx.compose.ui.geometry.Size

object  ArtUrlProvider {

    fun provideBySize(artUrl: String, size: Size): String {
        return artUrl.replace("{width}","${size.width.toInt()}").replace("{height}", "${size.height.toInt()}")
    }
    fun provideDefaultSize(artUrl: String,): String {
        return artUrl.replace("{width}","").replace("{height}", "")
    }
    fun provideScaledSize(artUrl: String,scale: Float): String {
        val scaledSize = Size(600f,800f).apply {
            this * scale
        }
        return artUrl
            .replace("{width}",scaledSize.width.toInt().toString())
            .replace("{height}", scaledSize.height.toInt().toString())
    }
}
