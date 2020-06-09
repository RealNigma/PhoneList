package com.realnigma.phonelist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.realnigma.phonelist.repository.PhoneRepository
import com.realnigma.phonelist.room.Phone
import com.realnigma.phonelist.room.PhoneDatabase
import com.realnigma.phonelist.room.PhoneWithImages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhoneViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: PhoneRepository

    val phones: LiveData<List<Phone>>
    var phoneWithImagesById: LiveData<PhoneWithImages>? = null

    init {
        val phoneDao = PhoneDatabase.getDatabase(application, viewModelScope).phoneDao()
        repository = PhoneRepository(phoneDao)
        phones = repository.phones
    }

    fun updatePhone(phone : Phone) = viewModelScope.launch(Dispatchers.IO) {
        repository.updatePhone(phone)
    }

    fun getPhoneWithImagesById(id : Int) = viewModelScope.launch(Dispatchers.IO) {
        phoneWithImagesById = repository.getPhoneWithImagesById(id)
    }
}