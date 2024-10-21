package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun WeekSelector(
    modifier: Modifier = Modifier,
    startOfWeek: String,
    endOfWeek: String,
    onPreviousButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .background(MaterialTheme.colors.onBackground, CircleShape),
                onClick = onPreviousButtonClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = null,
                    tint = MaterialTheme.colors.background
                )
            }
        }

        Box(
            modifier = Modifier.weight(3f)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "$startOfWeek - $endOfWeek",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .background(MaterialTheme.colors.onBackground, CircleShape),
                onClick = onNextButtonClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    tint = MaterialTheme.colors.background
                )
            }
        }
    }
}