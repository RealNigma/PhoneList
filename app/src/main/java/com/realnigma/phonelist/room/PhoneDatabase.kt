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
                var phone = Phone(0, "Meizu M6b", 5f)
                var urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30031750b.jpg",
                    "https://img.mvideo.ru/Pdb/30031750b1.jpg",
                    "https://img.mvideo.ru/Pdb/30031750b2.jpg")
                var phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))

                phone = Phone(0, "Samsung Galaxy S10", 0f)
                urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30042520b.jpg",
                    "https://img.mvideo.ru/Pdb/30042520b1.jpg",
                    "https://img.mvideo.ru/Pdb/30042520b2.jpg")
                phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))

                phone = Phone(0, "Apple iPhone SE 2020 RED", 3f)
                urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30049497b.jpg",
                    "https://img.mvideo.ru/Pdb/30049497b1.jpg",
                    "https://img.mvideo.ru/Pdb/30049497b2.jpg"
                )
                phoneId = phoneDao.insertPhone(phone)
                phoneDao.insertImageList(createImageList(phoneId.toInt(), urlList))

                phone = Phone(0, "Xiaomi Mi Note 10 Pro", 3f)
                urlList = listOf(
                    "https://img.mvideo.ru/Pdb/30046975b.jpg",
                    "https://img.mvideo.ru/Pdb/30046975b1.jpg",
                    "https://img.mvideo.ru/Pdb/30046975b2.jpg"
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