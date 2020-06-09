package com.realnigma.phonelist

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class PhoneRepository (private val phoneDao: PhoneDao) {

    val phones : LiveData<List<Phone>> = phoneDao.getAllPhones()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPhone (phone : Phone) {
        phoneDao.insertPhone(phone)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertImageList(images : List<PhoneImage>) {
        phoneDao.insertImageList(images)

    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPhoneWithImagesById(id: Int) : LiveData<PhoneWithImages> {
        return phoneDao.getPhoneWithImageById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePhone(phone: Phone) {
        phoneDao.updatePhone(phone)
    }
}