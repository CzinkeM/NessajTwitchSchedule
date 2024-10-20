package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.github.czinkem.nessaj_twitch_schedule.domain.ArtUrlProvider
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun SegmentCard(
    modifier: Modifier = Modifier,
    state: SegmentCardState,
) {

    val cardColor by remember(state) {
        derivedStateOf {
            state.category.color()
        }
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(2.dp, MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
            ) {
                CoilImage(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                    ,
                    imageModel = {
                        ArtUrlProvider.provideScaledSize(state.artUrl, .5f)
                    },
                    imageOptions = ImageOptions(
                        alignment = Alignment.Center,
                        contentScale = ContentScale.FillWidth,
                    ),
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    cardColor.copy(alpha = 0.1f),
                                    cardColor.copy(alpha = 1f)
                                ),
                            )
                        ),
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(cardColor)
                    .weight(1f)
            ) {
                Text(
                    text = state.timeString
                )
                Text(
                    text = state.title
                )
            }
        }
    }
}