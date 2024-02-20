package org.nautilusapp.nautilus.android.ui.composables.containers

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.nautilusapp.nautilus.DataResult
import org.nautilusapp.nautilus.Result
import org.nautilusapp.nautilus.android.ui.composables.indicators.PullRefreshIndicator
import kotlin.math.roundToInt


@Composable
fun Screen(content: @Composable () -> Unit) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() },
    ) {

        Column(
            modifier = Modifier
                .padding(32.dp)
                .verticalScroll(rememberScrollState(), enabled = true)
        ) {
            content()
        }
    }
}

@Composable
fun BlackScreen(content: @Composable () -> Unit) {
    Surface(
        color = Color.Black, modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
        ) {
            content()
        }
    }
}

const val PULL_REFRESH_HEIGHT = 16

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    onRefresh: suspend () -> DataResult<*>,
    snack: SnackbarHostState? = null,
    content: @Composable (SnackbarHostState?) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    val refreshState = rememberPullToRefreshState()

    val scrollState = rememberScrollState()

    val willRefresh by remember {
        derivedStateOf { refreshState.progress > 1f }
    }

    val haptics = LocalHapticFeedback.current

    LaunchedEffect(willRefresh) {
        if (willRefresh) {
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

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
                    println(res.error)
                    snack?.showSnackbar(
                        "Failed to refresh: ${res.error.message}",
                        duration = SnackbarDuration.Short
                    )
                }
            }
            refreshState.endRefresh()
        }
    }


    val scrollOffset by animateIntAsState(
        targetValue = when {
            refreshState.isRefreshing -> PULL_REFRESH_HEIGHT
            refreshState.progress in 0f..1f -> (PULL_REFRESH_HEIGHT * refreshState.progress).roundToInt()
            refreshState.progress > 1f -> (PULL_REFRESH_HEIGHT + ((refreshState.progress - 1f) * .1f) * 100).roundToInt()
            else -> 0
        }, label = "scrollOffset"
    )

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier
        .fillMaxSize()
        .nestedScroll(refreshState.nestedScrollConnection)
//        .padding(top = (PULL_REFRESH_HEIGHT / 2).dp)
        .clickable(
            indication = null,
            interactionSource = interactionSource
        )
        { focusManager.clearFocus() }
    ) {
        PullRefreshIndicator(refreshState = refreshState)
        PullToRefreshContainer(
            state = refreshState,
            indicator = { },
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            modifier = Modifier
                .size(0.dp)
                .alpha(0f)
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState, enabled = true)
                .offset(y = (scrollOffset).dp)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
//            Spacer(modifier = Modifier.height(16.dp))
            content(snack)
//            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
