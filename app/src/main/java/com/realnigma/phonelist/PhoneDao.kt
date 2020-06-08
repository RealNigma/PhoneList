package com.realnigma.phonelist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PhoneDao {

    @Query("SELECT * FROM phone")
    fun getAllPhones(): LiveData<List<Phone>>

    @Transaction
    @Query("SELECT * FROM phone WHERE id = :id")
    fun getPhoneWithImageById(id : Int) : LiveData<PhoneWithImages>

    @Insert
    fun insertPhone(phone : Phone)

    @Insert
    fun insertImageList(images : List<PhoneImage>)

}