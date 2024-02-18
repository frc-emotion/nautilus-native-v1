package org.nautilusapp.nautilus.android.ui.composables.extras

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun BoxScope.DraggableScrollBar(
    scrollRange: () -> Float,
    isScrolling: Boolean,
    indicatorPosition: Double,
    onDragIndicator: suspend (Float) -> Unit,
    onScrollJump: suspend (Float) -> Unit,
) {
    val animatedIndicatorPosition by animateIntAsState(
        targetValue = indicatorPosition.roundToInt(),
        label = "scrollIndicator",
        animationSpec = tween(durationMillis = 10)
    )
    val interactionSource = remember { MutableInteractionSource() }
    var isDrag by remember { mutableStateOf(false) }
    var showHandle by remember { mutableStateOf(false)}
    LaunchedEffect(isDrag, isScrolling) {
        if(!isDrag && !isScrolling) delay(2000)
        showHandle = isDrag || isScrolling
    }
    val targetAlpha = if (isScrolling || isDrag || showHandle) 1f else 0f

    var offset by remember { mutableFloatStateOf(0f) }

    val haptics = LocalHapticFeedback.current
    LaunchedEffect(indicatorPosition) {
        if (isDrag) haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
    }

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        label = "scrollbarAlpha",
        animationSpec = tween(durationMillis = 700)
    )

    val color by animateColorAsState(
        targetValue = if (isDrag) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
        label = "color",
        animationSpec = tween(durationMillis = 500)
    )

    LaunchedEffect(offset) {
        if(isDrag) onDragIndicator(offset)
    }

    Box( //safe area to prevent interfering with scroll handle
        Modifier
            .fillMaxHeight()
            .width(48.dp)
            .align(Alignment.CenterEnd)
            .clickable(interactionSource = interactionSource, indication = null) {}
    ) {
        Box(
            Modifier
                .fillMaxHeight(0.9f)
                .width(36.dp)
                .align(Alignment.CenterEnd)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { (_, y) ->
                            val corrected = (y / scrollRange())
                            println(corrected)
                            if (!isDrag) {
                                onScrollJump(corrected)
                            }
                        },
                    )
                }
        ) {
            Canvas(Modifier
                .offset {
                    IntOffset(
                        y = animatedIndicatorPosition,
                        x = 0
                    )
                }
                .fillMaxWidth()
                .height(48.dp)
                .draggable(
                    orientation = Orientation.Vertical, state = rememberDraggableState {
                        offset = it / scrollRange()
                    },
                    onDragStarted = {
                        isDrag = true
                    },
                    onDragStopped = { isDrag = false }
                )
            ) {
                drawRoundRect(
                    color = color,
                    size = Size(4.dp.toPx(), 48.dp.toPx()),
                    alpha = alpha,
                    topLeft = Offset(24.dp.toPx(), 0f),
                    cornerRadius = CornerRadius(4.dp.toPx()),
                )
            }
        }
    }
}
