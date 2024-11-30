package com.github.czinkem.nessaj_twitch_schedule.presentation.screen.scheduleScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.github.czinkem.nessaj_twitch_schedule.domain.DateHelper
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek

data class ScheduleScreenState(
    val currentWeek:  StreamWeekState,
    val minusOneWeek: StreamWeekState,
    val minusTwoWeek: StreamWeekState,
    val plusOneWeek: StreamWeekState,
    val plusTwoWeek: StreamWeekState,
) {
    companion object {
        val EMPTY = ScheduleScreenState(
            currentWeek = StreamWeekState( emptyMap(),"",""),
            minusOneWeek = StreamWeekState( emptyMap(),"",""),
            minusTwoWeek = StreamWeekState( emptyMap(),"",""),
            plusOneWeek = StreamWeekState( emptyMap(),"",""),
            plusTwoWeek = StreamWeekState( emptyMap(),"",""),
        )
    }
}

data class StreamWeekState(
    val segments: Map<DayOfWeek, List<SegmentCardState>>,
    val startOfWeek: String,
    val endOfWeek: String
)

class ScheduleScreen: Screen {

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val uiCoroutineScope = rememberCoroutineScope()
        val screenModel = navigator.getNavigatorScreenModel<ScheduleScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        val isScheduleLoading by screenModel.isScheduleLoading.collectAsStateWithLifecycle()

        var sheetContentState: TwitchSegmentBottomSheetContentState? by remember {
            mutableStateOf(null)
        }

        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(2,0f) { 5 }
        val displayedStartWeek by remember(pagerState) {
            derivedStateOf {
                when(pagerState.currentPage) {
                    0 -> state.minusTwoWeek.startOfWeek
                    1 -> state.minusOneWeek.startOfWeek
                    2 -> state.currentWeek.startOfWeek
                    3 -> state.plusOneWeek.startOfWeek
                    4 -> state.plusTwoWeek.startOfWeek
                    else -> throw IllegalStateException()
                }
            }
        }
        val displayedEndOfWeek by remember(pagerState) {
            derivedStateOf {
                when(pagerState.currentPage) {
                    0 -> state.minusTwoWeek.endOfWeek
                    1 -> state.minusOneWeek.endOfWeek
                    2 -> state.currentWeek.endOfWeek
                    3 -> state.plusOneWeek.endOfWeek
                    4 -> state.plusTwoWeek.endOfWeek
                    else -> throw IllegalStateException()
                }
            }
        }

        Surface(
            color = MaterialTheme.colors.surface
        ) {
            Scaffold(
                bottomBar = {
                    AnimatedVisibility(!isScheduleLoading) {
                        WeekSelector(
                            modifier = Modifier
                                .background(color = MaterialTheme.colors.surface),
                            startOfWeek = displayedStartWeek,
                            endOfWeek = displayedEndOfWeek,
                            weekIndex = pagerState.currentPage,
                            onPreviousButtonClick = {
                                uiCoroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            },
                            onNextButtonClick = {
                                uiCoroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        )
                    }
                }
            ) { scaffoldPadding ->
                ModalBottomSheetLayout(
                    modifier = Modifier.padding(scaffoldPadding),
                    sheetContent = {
                        sheetContentState?.let {
                            TwitchSegmentBottomSheetContent(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                state = it,
                                closeSheet = {
                                    scope.launch {
                                        sheetState.hide()
                                    }
                                }
                            )
                        }
                    },
                    sheetState = sheetState,
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),

                    ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(MaterialTheme.colors.surface),
                    ) { page ->

                        val selectedWeek = when(page) {
                            0 -> state.minusTwoWeek
                            1 -> state.minusOneWeek
                            2 -> state.currentWeek
                            3 -> state.plusOneWeek
                            4 -> state.plusTwoWeek
                            else -> throw IllegalStateException()
                        }
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            if(isScheduleLoading) {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(MaterialTheme.colors.surface)
                                            .padding(top = 32.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier.align(Alignment.Center),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            CircularProgressIndicator()
                                            Spacer(modifier = Modifier.size(16.dp))
                                            Text(text = "Stream adatok betöltése")
                                        }
                                    }
                                }
                            }else {
                                if(selectedWeek.segments.isEmpty()) {
                                    item {
                                        NothingPlannedCard(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(all = 32.dp)
                                        )
                                    }
                                }else {
                                    selectedWeek.segments.forEach { (day, segments)->
                                        item {
                                            DayOfWeekHeader(
                                                modifier = Modifier.padding(vertical = 16.dp),
                                                dayOfWeek = day,
                                                selected = DateHelper.today().dayOfWeek == day
                                            )
                                        }
                                        items(segments) { segment ->
                                            SegmentCard(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .requiredHeight(150.dp)
                                                    .padding(vertical = 8.dp),
                                                state = segment,
                                                onClick = {
                                                    scope.launch {
                                                        sheetContentState = TwitchSegmentBottomSheetContentState(
                                                            title = segment.title,
                                                            categoryName = segment.categoryName,
                                                            isLive = DateHelper.isStreamLive(segment.startTime, segment.endTime),
                                                            time = segment.startTime
                                                        )
                                                        sheetState.show()
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
