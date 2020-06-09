package com.realnigma.phonelist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Phone::class, PhoneImage::class], version = 1)
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
                    .addCallback(
                        PhoneDatabaseCallback(
                            scope
                        )
                    )
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class PhoneDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.phoneDao())
                    }
                }
            }

            fun populateDatabase(phoneDao : PhoneDao) {
                val phone =
                    Phone(0, "iPhone 11", 4f)
                phoneDao.insertPhone(phone)
            }

        }

    }
}