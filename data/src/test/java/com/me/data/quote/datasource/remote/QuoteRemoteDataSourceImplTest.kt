package com.me.data.quote.datasource.remote


import com.me.data.quote.datasource.remote.api.IQuoteApi
import com.me.data.quoteData
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class QuoteRemoteDataSourceImplTest {

    private lateinit var dataSource: QuoteRemoteDataSourceImpl

    private val mockApi: IQuoteApi = mock(IQuoteApi::class.java)

    private val remoteItem = quoteData.copy(description = "remote")

    private val throwable = Throwable()


    @Before
    fun setUp() {
        dataSource = QuoteRemoteDataSourceImpl(mockApi)

    }

    @Test
    fun `get quote remote success`() {
        // given
        `when`(mockApi.getRandomQuote()).thenReturn(
            Single.just(
                remoteItem
            )
        )

        // when
        val test = dataSource.getRandomQuote().test()

        // then
        verify(mockApi).getRandomQuote()
        assert(test.values()[0].description == remoteItem.description)
    }

    @Test
    fun `get quote remote fail`() {
        // given
        `when`(mockApi.getRandomQuote()).thenReturn(Single.error(throwable))

        // when
        val test = dataSource.getRandomQuote().test()

        // then
        verify(mockApi).getRandomQuote()
        test.assertError(throwable)
    }

}