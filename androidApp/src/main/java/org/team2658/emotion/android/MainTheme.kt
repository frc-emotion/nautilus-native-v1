package org.team2658.emotion.android

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.team2658.emotion.android.ui.theme.DarkColors
import org.team2658.emotion.android.ui.theme.LightColors

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val colors = if (darkTheme) {
//        darkColors(
//            primary = Color(0xffdcc865),
//            primaryVariant = Color(0xFF1E2310),
//            secondary = Color(0xFFd0c762),
//            background = Color(0xFF121219),
//            onBackground = Color(0xFFDFE3F1)
//        )
//    } else {
//        lightColors(
//            primary = Color(0xFFF293B3),
//            primaryVariant = Color(0xFFed8cc8),
//            secondary = Color(0xFFEDEF76),
//            background = Color(0xFFFAFBDA),
//            onBackground = Color(0xFF282905),
//            onPrimary = Color(0xFFFAFBDA)
//        )
//    }

    //use dynamic colors on android 12+, else use premade color schemes
    val dynamicColorAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
//    val dynamicColorAvailable = false
    val colors = when {
        dynamicColorAvailable && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColorAvailable && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> DarkColors
        else -> LightColors
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
