package com.realnigma.phonelist

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.realnigma.phonelist.room.Phone
import com.realnigma.phonelist.room.PhoneDao
import com.realnigma.phonelist.room.PhoneDatabase
import com.realnigma.phonelist.room.PhoneImage
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class PhoneDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var phoneDao: PhoneDao
    private lateinit var database: PhoneDatabase

    @Before
    fun createDatabase() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, PhoneDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        phoneDao = database.phoneDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertAndGetPhone() {
        val phone = Phone(1, "Kotlin Phone", 5f)
        phoneDao.insertPhone(phone)
        val allPhones = phoneDao.getAllPhones().waitForValue()
        assertEquals(allPhones[0], phone)
    }

    @Test
    @Throws(IOException::class)
    fun getAllPhones() {
        val phone1 = Phone(1, "Kotlin Phone", 5f)
        phoneDao.insertPhone(phone1)

        val phone2 =
            Phone(2, "Hello World Phone", 1f)
        phoneDao.insertPhone(phone2)

        val allPhones = phoneDao.getAllPhones().waitForValue()
        assertEquals(allPhones[0], phone1)
        assertEquals(allPhones[1], phone2)
    }

    @Test
    @Throws(IOException::class)
    fun updatePhone() {
        val phone = Phone(1, "Kotlin Phone", 5f)
        phoneDao.insertPhone(phone)

        val updatedPhone =
            Phone(1, "Hello World Phone", 1f)
        phoneDao.updatePhone(updatedPhone)

        val allPhones = phoneDao.getAllPhones().waitForValue()
        assertEquals(allPhones[0], updatedPhone)
    }

    @Test
    @Throws(IOException::class)
    fun insertImageList() {

        val phone = Phone(1, "Kotlin Phone", 5f)
        phoneDao.insertPhone(phone)

        val phoneImage1 = PhoneImage(
            1,
            1,
            "www.kotlin.com/phone.jpg"
        )
        val phoneImage2 = PhoneImage(
            2,
            1,
            "www.kotlin.com/phone2.jpg"
        )
        val imageList = listOf(phoneImage1, phoneImage2)
        phoneDao.insertImageList(imageList)

        val phoneWitImages = phoneDao.getPhoneWithImageById(1).waitForValue()
        assertEquals(phoneWitImages.phone, phone)
        assertEquals(phoneWitImages.images[0], phoneImage1)
        assertEquals(phoneWitImages.images[1], phoneImage2)
    }

}