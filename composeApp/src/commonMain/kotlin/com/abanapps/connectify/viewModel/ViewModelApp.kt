package com.abanapps.connectify.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abanapps.connectify.appDatabase.Contacts
import com.abanapps.connectify.repo.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModelApp(private val repo: Repo) : ViewModel() {

    private val contactList = MutableStateFlow<List<Contacts>>(emptyList())
    val _contactsList = contactList.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAllContacts().collect {
                contactList.value = it
            }
        }

    }

    suspend fun deleteContact(contacts: Contacts) = repo.delete(contacts)

    suspend fun insertContact(contacts: Contacts) = repo.insert(contacts)

    suspend fun updateContact(contacts: Contacts) = repo.update(contacts)


}