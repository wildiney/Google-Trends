package com.wildiney.google_trends

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.wildiney.google_trends.ui.TrendsScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        TrendsScreen()
    }
}