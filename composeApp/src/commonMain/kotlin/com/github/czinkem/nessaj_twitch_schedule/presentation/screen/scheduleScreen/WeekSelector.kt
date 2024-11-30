package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeekSelector(
    modifier: Modifier = Modifier,
    startOfWeek: String,
    endOfWeek: String,
    weekIndex: Int,
    onPreviousButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
            color = MaterialTheme.colors.primary,
        )
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.medium),
                onClick = onPreviousButtonClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }

            Column(
                modifier = Modifier.weight(3f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier,
                    text = "$startOfWeek - $endOfWeek",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
                PageIndicator(
                    modifier = Modifier,
                    pageCount = 5,
                    currentPageIndex = weekIndex
                )
            }

            IconButton(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, MaterialTheme.colors.primary, MaterialTheme.shapes.medium),
                onClick = onNextButtonClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPageIndex: Int,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(pageCount) { index ->
            if(index == currentPageIndex) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.RadioButtonChecked,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            } else {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Filled.Circle,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}