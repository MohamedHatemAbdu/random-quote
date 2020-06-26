package com.me.data.quote.datasource.local.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.me.data.QuoteFactory
import com.me.data.base.AppDatabase
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class IQuoteDaoTest {

    private lateinit var quoteDao: IQuoteDao
    private lateinit var db: AppDatabase

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
        quoteDao = db.getQuoteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertQuotesListThenReturnTheInsertedQuotesList() {
        val cachedList = QuoteFactory.Factory.makeQuotesList(5).sortedBy { it.id }

        quoteDao.saveAllQuotes(cachedList)

        val retrievedList = quoteDao.getAllQuotes().blockingGet().sortedBy { it.id }

        assertEquals(retrievedList, cachedList)
    }

    @Test
    fun insertTwoListsWithSameIdsThenReturnTheNewlyInsertedList() {
        val cachedList1 = QuoteFactory.Factory.makeQuotesList(5).sortedBy { it.id }

        val cachedList2 = QuoteFactory.Factory.makeQuotesListSameIdsDiffCont(cachedList1)
            .sortedBy { it.id }


        quoteDao.saveAllQuotes(cachedList1)
        quoteDao.saveAllQuotes(cachedList2)

        assertNotEquals(cachedList1, cachedList2)

        val retrievedList = quoteDao.getAllQuotes().blockingGet().sortedBy { it.id }

        assertNotEquals(retrievedList, cachedList1)

        assertEquals(retrievedList, cachedList2)
    }

    @Test
    fun updateQuoteThenReturnTheUpdatedQuote() {
        val cachedItem = QuoteFactory.Factory.makeQuoteData()
        val updatedCachedItem = QuoteFactory.Factory.makeQuoteData(cachedItem.id)

        quoteDao.saveQuote(cachedItem)
        quoteDao.updateQuote(updatedCachedItem).blockingAwait()

        val test = quoteDao.getQuote(cachedItem.id).test()

        test.assertValue(updatedCachedItem)
        test.assertValueCount(1)
        test.assertNoErrors()
    }

    @Test
    fun removeQuoteThenReturnNotReturnTheRemovedQuote() {
        val cachedItem = QuoteFactory.Factory.makeQuoteData()
        quoteDao.saveQuote(cachedItem)
        quoteDao.deleteQuote(cachedItem.id).blockingAwait()

        val test = quoteDao.getQuote(cachedItem.id).test()

        test.assertNoValues()
        test.assertValueCount(0)
        test.assertError(EmptyResultSetException::class.java)
    }
}