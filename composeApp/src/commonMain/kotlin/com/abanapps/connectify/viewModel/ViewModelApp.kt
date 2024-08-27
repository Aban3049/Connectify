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

    fun deleteContact(contacts: Contacts) = viewModelScope.launch {
        repo.delete(contacts)
    }

    fun insertContact(contacts: Contacts) = viewModelScope.launch { repo.insert(contacts) }

    fun updateContact(contacts: Contacts) = viewModelScope.launch {
        repo.update(contacts)
    }


}