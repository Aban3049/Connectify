package com.abanapps.connectify

import androidx.compose.ui.window.ComposeUIViewController
import com.abanapps.connectify.appDatabase.DBFactory

fun MainViewController() = ComposeUIViewController {
    val db = DBFactory().createDatabase()
    App(db)
}