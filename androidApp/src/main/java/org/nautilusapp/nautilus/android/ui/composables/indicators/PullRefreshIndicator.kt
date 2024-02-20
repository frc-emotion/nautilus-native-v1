package org.nautilusapp.nautilus.android.ui.composables.indicators

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.MainTheme
import org.nautilusapp.nautilus.android.ui.composables.containers.BlackScreen
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullRefreshIndicator(
    refreshState: PullToRefreshState,
) {
    val willRefresh by remember {
        derivedStateOf{refreshState.progress > 1f}
    }
    if(refreshState.progress > 0 || refreshState.isRefreshing) {
        Box (
            Modifier
                .height(IntrinsicSize.Min)
                .padding(start = 16.dp, end = 16.dp )
                .alpha(if(refreshState.isRefreshing) 1.0f else (refreshState.progress*4).coerceAtMost(1.0f))
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter){
            when {
                willRefresh || refreshState.isRefreshing -> LinearProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier.fillMaxWidth()
                )
                refreshState.progress in 0f..1f -> LinearProgressIndicator(
                    progress = { refreshState.progress },
                    color = MaterialTheme.colorScheme.primary,
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(apiLevel = 33)
@Composable
fun IndPrev() {
    MainTheme(preference = ColorTheme.NAUTILUS_MIDNIGHT) {
        BlackScreen {
            PullRefreshIndicator(refreshState = rememberPullToRefreshState().apply { startRefresh() })
        }
    }
}