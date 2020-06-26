package com.me.data.quote.repository

import com.me.data.quote.datasource.local.QuoteCacheDataSourceImpl
import com.me.data.quote.datasource.remote.QuoteRemoteDataSourceImpl
import com.me.data.quoteData
import com.me.data.quoteEntity
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class QuoteRepositoryImplTest {

    private lateinit var repository: QuoteRepositoryImpl

    private val mockCacheDataSource: QuoteCacheDataSourceImpl = mock(QuoteCacheDataSourceImpl::class.java)
    private val mockRemoteDataSource: QuoteRemoteDataSourceImpl = mock(QuoteRemoteDataSourceImpl::class.java)

    private val id = quoteData.id

    private val cacheItem = quoteEntity.copy(description = "cache")
    private val remoteItem = quoteEntity.copy(description = "remote")



    private val throwable = Throwable()

    @Before
    fun setUp() {
        repository = QuoteRepositoryImpl(mockCacheDataSource, mockRemoteDataSource)
    }



    @Test
    fun `get quote cache fail fallback remote succeeds`() {

        // given
        `when`(mockCacheDataSource.getLatestQuote()).thenReturn(Single.error(throwable))
        `when`(mockRemoteDataSource.getRandomQuote()).thenReturn(Single.just(remoteItem))
        `when`(mockCacheDataSource.addQuote(remoteItem)).thenReturn(Single.just(remoteItem))

        // when
        val test = repository.getQuote(false).test()

        // then
        verify(mockCacheDataSource).getLatestQuote()
        verify(mockRemoteDataSource).getRandomQuote()
        verify(mockCacheDataSource).addQuote(remoteItem)
        test.assertValue(remoteItem)
    }

    @Test
    fun `get quote cache fail fallback remote fails`() {

        // given
        `when`(mockCacheDataSource.getLatestQuote()).thenReturn(Single.error(throwable))
        `when`(mockRemoteDataSource.getRandomQuote()).thenReturn(Single.error(throwable))

        // when
        val test = repository.getQuote(false).test()

        // then
        verify(mockCacheDataSource).getLatestQuote()
        verify(mockRemoteDataSource).getRandomQuote()
        test.assertError(throwable)
    }

    @Test
    fun `get quote caches success`() {
        // given
        `when`(mockCacheDataSource.getLatestQuote()).thenReturn(Single.just(cacheItem))

        // when
        val test = repository.getQuote(false).test()

        // then
        verify(mockCacheDataSource).getLatestQuote()
        test.assertValue(cacheItem)
    }

    @Test
    fun `get quote remote success`() {
        // given
        `when`(mockRemoteDataSource.getRandomQuote()).thenReturn(Single.just(remoteItem))
        `when`(mockCacheDataSource.addQuote(remoteItem)).thenReturn(Single.just(remoteItem))

        // when
        val test = repository.getQuote(true).test()

        // then
        verify(mockRemoteDataSource).getRandomQuote()
        verify(mockCacheDataSource).addQuote(remoteItem)
        test.assertValue(remoteItem)
    }

    @Test
    fun `get quote remote fail`() {
        // given
        `when`(mockRemoteDataSource.getRandomQuote()).thenReturn(Single.error(throwable))

        // when
        val test = repository.getQuote(true).test()

        // then
        verify(mockRemoteDataSource).getRandomQuote()
        test.assertError(throwable)
    }
    @Test
    fun `clear quotes cache success`() {
        // given
        `when`(mockCacheDataSource.clearAllQuotes()).thenReturn(Completable.complete())

        // when
        val test = repository.clearAllQuotes().test()

        // then
        verify(mockCacheDataSource).clearAllQuotes()
        test.assertComplete()
    }


}