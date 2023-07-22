package org.team2658.emotion.android.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Screen(content: @Composable ()->Unit){
    Surface(color= MaterialTheme.colorScheme.background, modifier = Modifier
        .fillMaxSize()
    ){
        Column(modifier= Modifier
            .padding(32.dp)
            .verticalScroll(rememberScrollState(), enabled = true)
        ) {
            content()
        }
    }
}

