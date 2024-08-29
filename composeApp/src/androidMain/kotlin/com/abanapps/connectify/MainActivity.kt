package com.abanapps.connectify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.abanapps.connectify.appDatabase.DBFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = DBFactory(applicationContext).createDatabase()
        setContent {
            App(db)
        }
    }
}


