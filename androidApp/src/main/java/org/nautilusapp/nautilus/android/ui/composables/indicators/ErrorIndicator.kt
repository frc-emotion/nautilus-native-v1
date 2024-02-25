package org.nautilusapp.nautilus.android.ui.composables.indicators

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.nautilusapp.nautilus.android.PreviewTheme
import org.nautilusapp.nautilus.android.ui.composables.ThemePreviewProvider
import org.nautilusapp.nautilus.android.ui.composables.containers.Screen
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme

@Composable
fun ErrorIndicator(text: String) {
    Card(
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Error,
                contentDescription = "Edit",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(AssistChipDefaults.IconSize)
            )
            Spacer(Modifier.size(4.dp))
            Text(text = text, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun WarningIndicator(text: String) {
    Card(
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = WARNING_COLOR),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = "Edit",
                tint = Color.Black,
                modifier = Modifier.size(AssistChipDefaults.IconSize)
            )
            Spacer(Modifier.size(4.dp))
            Text(text = text, color = Color.Black)
        }
    }
}

val WARNING_COLOR = Color(0xFFE0AC00)

@Composable
fun MinimalWarning(text: String) {

    Row(
        modifier = Modifier
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.Warning,
            contentDescription = null,
            tint = WARNING_COLOR,
            modifier = Modifier
                .size(AssistChipDefaults.IconSize)
        )
        Spacer(Modifier.size(4.dp))
        Text(
            text = text, color = WARNING_COLOR,
        )
    }
}

@Composable
fun InfoIndicator(text: String) {
    Card(
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFffe017)),
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Info,
                contentDescription = "Edit",
                tint = Color.Black,
                modifier = Modifier.size(AssistChipDefaults.IconSize)
            )
            Spacer(Modifier.size(4.dp))
            Text(text = text, color = Color.Black)
        }
    }
}

@Preview
@Composable
fun ErrorIndicatorPreview(
    @PreviewParameter(ThemePreviewProvider::class) theme: ColorTheme,
) {
    PreviewTheme(preference = theme) {
        Screen {
            ErrorIndicator("This is an error")
            Spacer(Modifier.size(16.dp))
            WarningIndicator("This is a warning")
            MinimalWarning(text = "This is a minimal warning")
            Spacer(Modifier.size(16.dp))
            InfoIndicator("This is an info")
        }
    }
}