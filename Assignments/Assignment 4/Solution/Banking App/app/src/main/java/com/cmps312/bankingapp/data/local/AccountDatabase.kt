package com.cmps312.bankingapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cmps312.bankingapp.data.local.entity.AccTransaction
import com.cmps312.bankingapp.data.local.entity.Account


@Database(entities = [Account::class, AccTransaction::class], version = 1, exportSchema = false)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao

    // a single database instance
    companion object {
        @Volatile
        private var database: AccountDatabase? = null
        private val DB_NAME = "accounts.db"

        @Synchronized
        fun getDatabase(context: Context): AccountDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    AccountDatabase::class.java,
                    DB_NAME
                ).build()
            }
            return database as AccountDatabase
        }
    }
}

