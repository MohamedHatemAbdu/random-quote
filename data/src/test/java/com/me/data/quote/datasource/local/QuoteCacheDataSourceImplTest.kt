package com.me.data.quote.datasource.local


import com.me.data.base.AppDatabase
import com.me.data.quote.datasource.local.dao.IQuoteDao
import com.me.data.quote.model.mapToDomain
import com.me.data.quoteData
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class QuoteCacheDataSourceImplTest {

    private lateinit var dataSource: QuoteCacheDataSourceImpl

    private var mockedDatabase: AppDatabase = mock(AppDatabase::class.java)
    private var mockedQuoteDao: IQuoteDao = mock(IQuoteDao::class.java)

    private val id = quoteData.id

    private val cacheItem = quoteData.copy(description = "cached")
    private val cacheList = listOf(cacheItem)


    private val throwable = Throwable()

    @Before
    fun setUp() {
        `when`(mockedDatabase.getQuoteDao()).thenReturn(mockedQuoteDao)
        dataSource = QuoteCacheDataSourceImpl(mockedDatabase)
    }

    @Test
    fun `get all quotes cache success`() {
        // given
        `when`(mockedQuoteDao.getAllQuotes()).thenReturn(Single.just(cacheList))

        // when
        val test = dataSource.getQuotes().test()

        // then
        verify(mockedQuoteDao).getAllQuotes()
        test.assertValue(cacheList.mapToDomain())
    }

    @Test
    fun `get all quotes cache fail`() {
        // given
        `when`(mockedQuoteDao.getAllQuotes()).thenReturn(Single.error(throwable))

        // when
        val test = dataSource.getQuotes().test()

        // then
        verify(mockedQuoteDao).getAllQuotes()
        test.assertError(throwable)
    }

    @Test
    fun `set all quotes cache success`() {
        // given
        `when`(mockedQuoteDao.saveAllQuotes(cacheList)).thenReturn(listOf())
        `when`(mockedQuoteDao.getAllQuotes()).thenReturn(Single.just(cacheList))

        // when
        val test = dataSource.setQuotes(cacheList.mapToDomain()).test()

        // then
        // then
        verify(mockedQuoteDao).saveAllQuotes(cacheList)
        verify(mockedQuoteDao).getAllQuotes()

        test.assertValue(cacheList.mapToDomain())
        test.assertNoErrors()

    }

    @Test
    fun `set all quotes cache fail`() {
        // given
        `when`(mockedQuoteDao.saveAllQuotes(cacheList)).thenReturn(listOf())
        `when`(mockedQuoteDao.getAllQuotes()).thenReturn(Single.error(throwable))

        // when
        val test = dataSource.setQuotes(cacheList.mapToDomain()).test()

        // then
        // then
        verify(mockedQuoteDao).saveAllQuotes(cacheList)
        verify(mockedQuoteDao).getAllQuotes()

        test.assertError(throwable)
    }

    @Test
    fun `get quote by id cache success`() {
        // given
        `when`(mockedQuoteDao.getQuote(id)).thenReturn(Single.just(cacheItem))

        // when
        val test = dataSource.getQuote(id).test()

        // then
        verify(mockedQuoteDao).getQuote(id)
        test.assertValue(cacheItem.mapToDomain())
    }

    @Test
    fun `get quote by id cache fail`() {
        // given
        `when`(mockedQuoteDao.getQuote(id)).thenReturn(Single.error(throwable))

        // when
        val test = dataSource.getQuote(id).test()

        // then
        verify(mockedQuoteDao).getQuote(id)
        test.assertError(throwable)
    }


    @Test
    fun `edit quote cache success`() {
        // given
        `when`(mockedQuoteDao.updateQuote(cacheItem)).thenReturn(Completable.complete())

        // when
        val test = dataSource.editQuote(cacheItem.mapToDomain()).test()

        // then
        verify(mockedQuoteDao).updateQuote(cacheItem)
        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun `edit quote cache fail`() {
        // given
        `when`(mockedQuoteDao.updateQuote(cacheItem)).thenReturn(Completable.error(throwable))

        // when
        val test = dataSource.editQuote(cacheItem.mapToDomain()).test()

        // then

        verify(mockedQuoteDao).updateQuote(cacheItem)
        test.assertError(throwable)
    }


    @Test
    fun `add quote cache success`() {
        // given
        `when`(mockedQuoteDao.saveQuote(cacheItem)).thenReturn(1)
        `when`(mockedQuoteDao.getQuote(cacheItem.id)).thenReturn(
            Single.just(
                cacheItem
            )
        )

        // when
        val test = dataSource.addQuote(cacheItem.mapToDomain()).test()

        // then
        verify(mockedQuoteDao).saveQuote(cacheItem)
        verify(mockedQuoteDao).getQuote(cacheItem.id)

        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun `add quote cache fail`() {
        // given
        `when`(mockedQuoteDao.saveQuote(cacheItem)).thenReturn(0)
        `when`(mockedQuoteDao.getQuote(cacheItem.id)).thenReturn(
            Single.error(
                throwable
            )
        )


        // when
        val test = dataSource.addQuote(cacheItem.mapToDomain()).test()

        // then
        verify(mockedQuoteDao).saveQuote(cacheItem)
        verify(mockedQuoteDao).getQuote(cacheItem.id)

        test.assertError(throwable)
    }


    @Test
    fun `delete quote cache success`() {
        // given
        `when`(mockedQuoteDao.deleteQuote(id)).thenReturn(Completable.complete())

        // when
        val test = dataSource.deleteQuote(id).test()

        // then
        verify(mockedQuoteDao).deleteQuote(id)
        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun `delete quote cache fail`() {
        // given
        `when`(mockedQuoteDao.deleteQuote(id)).thenReturn(
            Completable.error(
                throwable
            )
        )

        // when
        val test = dataSource.deleteQuote(id).test()

        // then
        verify(mockedQuoteDao).deleteQuote(id)
        test.assertError(throwable)
    }

}