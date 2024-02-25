package org.nautilusapp.nautilus.android.ui.composables

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme

class ThemePreviewProvider : PreviewParameterProvider<ColorTheme> {
    override val values: Sequence<ColorTheme>
        get() = sequenceOf(
            *ColorTheme.entries.toTypedArray()
        )
}
