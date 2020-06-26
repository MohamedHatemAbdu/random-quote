package com.me.data.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.me.data.quote.datasource.local.dao.IQuoteDao
import com.me.data.quote.model.QuoteData

@Database(entities = [QuoteData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getQuoteDao(): IQuoteDao
}