package com.abanapps.connectify.appDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contacts(
    val name:String,
    val email:String,
    val phoneNo:String,
    val address:String,
    val imageUrl:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
)
