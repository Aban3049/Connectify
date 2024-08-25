package com.abanapps.connectify.repo

import com.abanapps.connectify.appDatabase.Contacts
import com.abanapps.connectify.appDatabase.ContactsDao
import kotlinx.coroutines.flow.flow

class Repo(private val contactRepo: ContactsDao) {


    suspend fun insert(contact: Contacts) {
        contactRepo.insertContact(contact)
    }

    suspend fun update(contact: Contacts){
        contactRepo.updateContact(contact)
    }

    suspend fun getAllContacts() = flow<List<Contacts>> {
        contactRepo.getAllContacts().collect {
            emit(it)
        }
    }

    suspend fun delete(contact: Contacts){
        contactRepo.deleteContact(contact)
    }


}