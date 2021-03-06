package com.realnigma.phonelist.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PhoneDao {

    @Query("SELECT * FROM phone")
    fun getAllPhones(): LiveData<List<Phone>>

    @Transaction
    @Query("SELECT * FROM phone WHERE id = :id")
    fun getPhoneWithImageById(id : Int) : LiveData<PhoneWithImages>

    @Insert
    fun insertPhone(phone : Phone) : Long

    @Insert
    fun insertImageList(images : List<PhoneImage>)

    @Update
    fun updatePhone(phone: Phone)

}