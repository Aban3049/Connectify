package com.abanapps.connectify.appDatabase

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSHomeDirectory

actual class DBFactory {
    actual fun createDatabase(): ContactsDatabase {
        val dbFile = NSHomeDirectory() + dbFileName
        return Room.databaseBuilder<ContactsDatabase>(
            dbFile,
            factory = {ContactsDatabase::class.instantiateImpl()})
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}