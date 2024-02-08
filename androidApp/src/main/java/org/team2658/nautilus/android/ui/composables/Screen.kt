package org.team2658.nautilus.android.ui.composables

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random


@Composable
fun Screen(content: @Composable () -> Unit) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier
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

const val PULL_REFRESH_HEIGHT = 116
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Screen(onRefresh: suspend () -> Unit, content: @Composable () -> Unit) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val scope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        scope.launch {
            refreshing = true
            onRefresh()
            refreshing = false
        }
    })

    val scrollState = rememberScrollState()

    val willRefresh by remember {
        derivedStateOf{refreshState.progress > 1f}
    }

    val haptics = LocalHapticFeedback.current

    LaunchedEffect(willRefresh) {
        if(willRefresh) {
            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
        }
    }

    val scrollOffset by animateIntAsState(
        targetValue = when {
            refreshing -> PULL_REFRESH_HEIGHT
            refreshState.progress in 0f..1f -> (PULL_REFRESH_HEIGHT * refreshState.progress).roundToInt()
            refreshState.progress > 1f -> (PULL_REFRESH_HEIGHT + ((refreshState.progress - 1f) * .1f) * 100).roundToInt()
            else -> 0
        }, label = "scrollOffset"
    )


    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier
        .fillMaxSize()
        .clickable(
            indication = null,
            interactionSource = interactionSource
        )
        { focusManager.clearFocus() }
    ) {

        if(refreshState.progress > 0 || refreshing) {
            Box (Modifier.height(IntrinsicSize.Min).fillMaxWidth().offset(y=(scrollOffset/4).coerceAtLeast(10).dp), contentAlignment = Alignment.TopCenter){
                when {
                    willRefresh || refreshing -> CircularProgressIndicator(Modifier.size(64.dp), strokeCap = StrokeCap.Round)
                    refreshState.progress in 0f..1f -> CircularProgressIndicator(modifier = Modifier.size(64.dp), progress = refreshState.progress, strokeCap = StrokeCap.Round)
                }

            }
        }

        Column(
            modifier = Modifier
                .padding(32.dp)
                .pullRefresh(refreshState)
                .verticalScroll(scrollState, enabled = true)
                .offset(y = (scrollOffset).dp)
        ) {
            content()
        }
    }
}

@Composable
@Preview
fun ScreenPreview() {
    val strings = (1..300).map { "meow" }
    var color by remember { mutableStateOf(Color(0xFF99FFEE)) }
    Screen (onRefresh = { color = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f) }) {
        strings.forEach {
            Surface(color = color) {
                Text(it, modifier = Modifier.padding(16.dp))
            }
        }
    }
}