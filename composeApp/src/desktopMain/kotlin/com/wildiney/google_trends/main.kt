package com.wildiney.google_trends

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.wildiney.google_trends.ui.screen.TrendsScreenDesktop

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Google Trends",
        state = WindowState(
            width = 350.dp,
            height = 700.dp
        )
    ) {
        TrendsScreenDesktop()
    }
}