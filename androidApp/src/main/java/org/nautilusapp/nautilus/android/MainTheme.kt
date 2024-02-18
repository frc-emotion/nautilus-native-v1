package org.nautilusapp.nautilus.android

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.nautilusapp.nautilus.android.ui.theme.ColorTheme
import org.nautilusapp.nautilus.android.ui.theme.DarkColors
import org.nautilusapp.nautilus.android.ui.theme.LightColors

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    preference: ColorTheme,
    content: @Composable () -> Unit
) {

    /**
     * View colors at [realtime colors](https://www.realtimecolors.com/?colors=e0fef4-000000-31f9c7-78f885-d6ff98&fonts=Poppins-Poppins)
     */
    val nautilusMidnight = darkColorScheme(
        primary = Color(0xFF31f9c7),
        background = Color.Black,
        secondary = Color(0xFF78f885),
        tertiary = Color(0xFFd6ff98),
        onBackground = Color(0xFFe0fef4),
        surface =  Color.Black,
        surfaceContainer = Color(0xFF1a1a1a),
        surfaceTint = Color.LightGray
    )

    /**
     * View at [realtime colors](https://www.realtimecolors.com/?colors=011e14-ffffff-06d09d-0fd3ca-ede865&fonts=Poppins-Poppins)
     */
    val nautilusLight = lightColorScheme(
        background = Color.White,
        onBackground = Color(0xFF011e14),
        primary = Color(0xFF06d09d),
        secondary = Color(0xFF0fd3ca),
        tertiary = Color(0xFFede865),
    )


    //use dynamic colors on android 12+, else use premade color schemes
    val dynamicColorAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val defaultDark = when {
        dynamicColorAvailable -> dynamicDarkColorScheme(LocalContext.current)
        else -> DarkColors
    }

    val defaultLight = when {
        dynamicColorAvailable -> dynamicLightColorScheme(LocalContext.current)
        else -> LightColors
    }

    val defaultColors = when {
        darkTheme -> defaultDark
        else -> defaultLight
    }

    val colors = when(preference) {
        ColorTheme.NAUTILUS_MIDNIGHT -> nautilusMidnight
        ColorTheme.MATERIAL3_DARK -> defaultDark
        ColorTheme.MATERIAL3_LIGHT -> defaultLight
        ColorTheme.MATERIAL3 -> defaultColors
        ColorTheme.NAUTILUS_LIGHT -> nautilusLight
    }

    val typography = Typography(
//        body1 = TextStyle(
//            fontFamily = FontFamily.SansSerif,
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp
//        )
        bodyMedium = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),

        )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        typography = typography,
        shapes = shapes,
        content = content,
        colorScheme = colors
    )
}

@Composable
fun cardColor() = MaterialTheme.colorScheme.surfaceContainerLow

@Composable
fun smallCardColor() = MaterialTheme.colorScheme.surfaceVariant