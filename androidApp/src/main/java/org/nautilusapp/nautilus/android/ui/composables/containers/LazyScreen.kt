package org.nautilusapp.nautilus.android.ui.composables.containers

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.nautilusapp.nautilus.DataResult
import org.nautilusapp.nautilus.Result
import org.nautilusapp.nautilus.android.ui.composables.extras.DraggableScrollBar
import org.nautilusapp.nautilus.android.ui.composables.indicators.PullRefreshIndicator
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyScreen(
    onRefresh: suspend () -> DataResult<*>,
    beforeLazyList: @Composable () -> Unit = {},
    snack: SnackbarHostState? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 32.dp),
    content: LazyListScope.(SnackbarHostState?) -> Unit
) {
    val state = rememberLazyListState()

    val listSize by remember {
        derivedStateOf {
            val avgVisSize =
                state.layoutInfo.visibleItemsInfo.sumOf { it.size } / state.layoutInfo.visibleItemsInfo.size.coerceAtLeast(
                    1
                )
            avgVisSize * state.layoutInfo.totalItemsCount
        }
    }

    var size by remember { mutableStateOf(IntSize.Zero) }
    val firstVis by remember { derivedStateOf { state.layoutInfo.visibleItemsInfo.firstOrNull() } }

    fun range(): Float = ((size.height) * 0.9f).coerceAtLeast(0f)
    val dpCorrection = with(LocalDensity.current) { 24.dp.toPx() }

    val haptics = LocalHapticFeedback.current

    val targetScrollIndicatorPosition by remember {
        derivedStateOf {
            val pos =
                (firstVis?.index ?: 0) * range() / state.layoutInfo.totalItemsCount.coerceAtLeast(1)
            val off = (firstVis?.offset ?: 0) * range() / listSize.coerceAtLeast(1)
            pos - 0.5 * dpCorrection - off
        }
    }


    val refreshState = rememberPullToRefreshState()

    val willRefresh by remember {
        derivedStateOf { refreshState.progress > 1f }
    }

    LaunchedEffect(willRefresh) {
        if (willRefresh) {
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
        }
        state.interactionSource.interactions
    }

    val pullRefreshOffset by animateIntAsState(
        targetValue = when {
            refreshState.isRefreshing -> PULL_REFRESH_HEIGHT
            refreshState.progress in 0f..1f -> (PULL_REFRESH_HEIGHT * refreshState.progress).roundToInt()
            refreshState.progress > 1f -> (PULL_REFRESH_HEIGHT + ((refreshState.progress - 1f) * .1f) * 100).roundToInt()
            else -> 0
        }, label = "pullRefreshOffset"
    )

    val scope = rememberCoroutineScope()
    if (refreshState.isRefreshing) {
        LaunchedEffect(true) {
            launch {
                snack?.showSnackbar("Refreshing...", duration = SnackbarDuration.Short)
            }
            when (val res = onRefresh()) {
                is Result.Success -> scope.launch {
                    snack?.showSnackbar(
                        "Refreshed successfully",
                        duration = SnackbarDuration.Short
                    )
                }

                is Result.Error -> scope.launch {
                    snack?.showSnackbar(
                        "Failed to refresh: ${res.error.message}",
                        duration = SnackbarDuration.Short
                    )
                }
            }
            refreshState.endRefresh()
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(top = (PULL_REFRESH_HEIGHT / 2).dp)
                .onSizeChanged { size = it }) {
            PullRefreshIndicator(refreshState)
            Column(
                Modifier
                    .fillMaxWidth()
                    .offset(y = pullRefreshOffset.dp)
                    .nestedScroll(refreshState.nestedScrollConnection)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                PullToRefreshContainer(
                    state = refreshState,
                    indicator = { },
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent,
                    modifier = Modifier
                        .size(0.dp)
                        .alpha(0f)
                )
                beforeLazyList()
                LazyColumn(
                    state = state,
                    contentPadding = contentPadding,
                    modifier = Modifier
                        .fillMaxWidth(),
                    content = {
                        content(snack)
                    }
                )
            }
            DraggableScrollBar(
                indicatorPosition = targetScrollIndicatorPosition,
                isScrolling = state.isScrollInProgress,
                onDragIndicator = {
                    state.scroll {
                        scrollBy(it * listSize)
                    }
                },
                scrollRange = ::range,
                onScrollJump = {
                    state.animateScrollToItem(
                        (it * state.layoutInfo.totalItemsCount)
                            .roundToInt()
                            .coerceIn(0, state.layoutInfo.totalItemsCount),
                    )
                }
            )
        }
    }
}