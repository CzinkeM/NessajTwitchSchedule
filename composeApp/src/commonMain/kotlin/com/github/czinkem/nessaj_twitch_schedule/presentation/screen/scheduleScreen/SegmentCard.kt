package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.czinkem.nessaj_twitch_schedule.domain.ArtUrlProvider
import com.github.czinkem.nessaj_twitch_schedule.domain.DateHelper
import com.github.czinkem.nessaj_twitch_schedule.domain.DateHelper.localDateTimeToHourMinuteString
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SegmentCard(
    modifier: Modifier = Modifier,
    state: SegmentCardState,
    onClick: () -> Unit
) {
    val density = LocalDensity.current
    val cardColor by remember(state) {
        derivedStateOf {
            state.category.color()
        }
    }

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(2.dp, cardColor),
        onClick = onClick
    ) {
        var imageWidth by remember {
            mutableStateOf(0.dp)
        }

        Row(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            Box(
                modifier = Modifier
            ) {
                CoilImage(
                    modifier = Modifier
                        .onGloballyPositioned {
                            imageWidth = with(density) { it.size.width.toDp() }
                        },
                    imageModel = {
                        ArtUrlProvider.provideDefaultSize(state.artUrl)
                    },
                    imageOptions = ImageOptions(
                        alignment = Alignment.Center,
                        contentScale = ContentScale.FillWidth,
                    ),
                )
                if (DateHelper.isTimePassed(state.startTime) && DateHelper.isTimePassed(state.endTime)) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(imageWidth)
                            .background(MaterialTheme.colors.surface.copy(alpha = .5f))
                    ) {
                        Icon(
                            modifier = Modifier.align(Alignment.Center).size(64.dp),
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.weight(2f)
            ) {
                CoilImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(20.dp),
                    imageModel = {
                        ArtUrlProvider.provideDefaultSize(state.artUrl)
                    },
                    imageOptions = ImageOptions(
                        alignment = Alignment.Center,
                        contentScale = ContentScale.FillWidth,
                    ),
                )
                Box(
                    modifier = Modifier.background(Color.Black.copy(alpha = .3f)).fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(4.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = localDateTimeToHourMinuteString(state.startTime) + " - " + localDateTimeToHourMinuteString(state.endTime),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.LightGray
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = state.categoryName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = state.title,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}