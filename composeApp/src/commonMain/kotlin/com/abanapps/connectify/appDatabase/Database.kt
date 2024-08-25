package com.abanapps.connectify.appDatabase

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Contacts::class], version = 1)
abstract class ContactsDatabase : RoomDatabase(), DB {

    abstract fun getDao(): ContactsDao

    override fun clearAllTables() {
        super.clearAllTables()
    }

}

internal const val dbFileName = "contacts.db"

interface DB {
    fun clearAllTables() {}
}