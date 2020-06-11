package com.realnigma.phonelist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Phone::class, PhoneImage::class], version = 2)
abstract class PhoneDatabase : RoomDatabase() {

    abstract fun phoneDao() : PhoneDao

    companion object {

        @Volatile
        private var INSTANCE: PhoneDatabase? = null

        fun getDatabase(
            context : Context,
            scope: CoroutineScope
        ) : PhoneDatabase {
            return  INSTANCE
                ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    PhoneDatabase::class.java,
                    "phone_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(PhoneDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class PhoneDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.phoneDao())
                    }
                }
            }

            fun populateDatabase(phoneDao : PhoneDao) {
                var phone = Phone(0, "Meizu MX6", 0f)
                var urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30026511b.jpg",
                    "https://img.mvideo.ru/Pdb/30026511b1.jpg",
                    "https://img.mvideo.ru/Pdb/30026511b2.jpg")
                var phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))

                phone = Phone(0, "Samsung Galaxy S10", 0f)
                urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30042520b.jpg",
                    "https://img.mvideo.ru/Pdb/30042520b1.jpg",
                    "https://img.mvideo.ru/Pdb/30042520b2.jpg")
                phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))

                phone = Phone(0, "Apple iPhone SE 2020 RED", 0f)
                urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30049497b.jpg",
                    "https://img.mvideo.ru/Pdb/30049497b1.jpg",
                    "https://img.mvideo.ru/Pdb/30049497b2.jpg"
                )
                phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))

                phone = Phone(0, "Xiaomi Mi Note 10 Pro", 0f)
                urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30046975b.jpg",
                    "https://img.mvideo.ru/Pdb/30046975b1.jpg",
                    "https://img.mvideo.ru/Pdb/30046975b2.jpg"
                )
                phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))

                phone = Phone(0, "OnePlus 7 Pro Mirror Gray", 0f)
                urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30046469b.jpg",
                    "https://img.mvideo.ru/Pdb/30046469b1.jpg",
                    "https://img.mvideo.ru/Pdb/30046469b2.jpg"
                )
                phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))

                phone = Phone(0, "Huawei P40 Pro Black", 0f)
                urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30048588b.jpg",
                    "https://img.mvideo.ru/Pdb/30048588b1.jpg",
                    "https://img.mvideo.ru/Pdb/30048588b2.jpg"
                )
                phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))

                phone = Phone(0, "ZTE Axon 10 Pro Blue", 0f)
                urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30045081b.jpg",
                    "https://img.mvideo.ru/Pdb/30045081b1.jpg",
                    "https://img.mvideo.ru/Pdb/30045081b2.jpg"
                )
                phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))

                phone = Phone(0, "OPPO A1k Black", 0f)
                urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30043639b.jpg",
                    "https://img.mvideo.ru/Pdb/30043639b1.jpg",
                    "https://img.mvideo.ru/Pdb/30043639b2.jpg"
                )
                phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))

                phone = Phone(0, "Honor 30 Premium Titanium Silver", 0f)
                urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30049877b.jpg",
                    "https://img.mvideo.ru/Pdb/30049877b2.jpg",
                    "https://img.mvideo.ru/Pdb/30049877b3.jpg"
                )
                phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))

                phone = Phone(0, "Realme 6 Pro Lightning Red", 0f)
                urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30048624b.jpg",
                    "https://img.mvideo.ru/Pdb/30048624b1.jpg",
                    "https://img.mvideo.ru/Pdb/30048624b2.jpg"
                )
                phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))


            }


            fun createImageList(phoneId : Int, urlList : List<String>) : List<PhoneImage> {
                val images : MutableList<PhoneImage> = ArrayList<PhoneImage>()
                for (url : String in urlList) {
                    val phoneImage = PhoneImage(0, phoneId, url)
                    images.add(phoneImage)
                }
                return images
            }

        }

    }
}