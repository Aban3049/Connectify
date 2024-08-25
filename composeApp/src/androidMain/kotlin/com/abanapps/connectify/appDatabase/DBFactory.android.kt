package com.abanapps.connectify.appDatabase

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

actual class DBFactory(private val context: Context) {
    actual fun createDatabase(): ContactsDatabase {
        val dbFile = context.getDatabasePath(dbFileName)
        return Room.databaseBuilder<ContactsDatabase>(context, dbFile.absolutePath)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

}