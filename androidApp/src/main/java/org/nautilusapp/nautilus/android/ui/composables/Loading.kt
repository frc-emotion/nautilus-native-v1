package org.nautilusapp.nautilus.android.ui.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingSpinner(isBusy: Boolean) {
    if(isBusy) {
        Dialog(onDismissRequest = {  }) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color(0x00000000))
            ) {
                CircularProgressIndicator(
                    Modifier.size(96.dp),
                    strokeCap = StrokeCap.Round,
                    color = MaterialTheme.colorScheme.primary
                    )
            }
        }
    }
}