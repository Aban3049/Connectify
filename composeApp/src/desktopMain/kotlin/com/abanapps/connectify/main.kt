package com.abanapps.connectify

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.abanapps.connectify.appDatabase.DBFactory

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Connectify",
    ) {
        val db = DBFactory().createDatabase()
        App(db)
    }
}