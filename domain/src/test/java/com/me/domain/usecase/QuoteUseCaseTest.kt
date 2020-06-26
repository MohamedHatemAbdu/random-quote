package com.me.domain.usecase


import com.me.domain.quote.repository.IQuoteRepository
import com.me.domain.quote.usecase.QuoteUseCase
import com.me.domain.quoteEntity
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


class QuoteUseCaseTest {

    private lateinit var quoteUseCase: QuoteUseCase
    private val mockQuoteRepository: IQuoteRepository = mock(IQuoteRepository::class.java)



    val throwable = Throwable()

    @Before
    fun setUp() {
        quoteUseCase = QuoteUseCase(mockQuoteRepository)
    }

    @Test
    fun `repository get Quote success`() {
        // given
        `when`(mockQuoteRepository.getQuote(false)).thenReturn(Single.just(quoteEntity))

        // when
        val test = quoteUseCase.getRandomQuote(false).test()

        // test
        verify(mockQuoteRepository).getQuote(false)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(quoteEntity)
    }

    @Test
    fun `repository get Quote fail`() {
        // given
        `when`(mockQuoteRepository.getQuote(false)).thenReturn(Single.error(throwable))

        // when
        val test = quoteUseCase.getRandomQuote(false).test()

        //
        // then
        verify(mockQuoteRepository).getQuote(false)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }


    @Test
    fun `delete cache quote success`() {
        // given
        `when`(mockQuoteRepository.clearAllQuotes()).thenReturn(Completable.complete())

        // when
        val test = quoteUseCase.clearAllQuotes().test()

        // test
        verify(mockQuoteRepository).clearAllQuotes()

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(0)
    }

    @Test
    fun `delete cache quote fail`() {
        // given
        `when`(mockQuoteRepository.clearAllQuotes()).thenReturn(Completable.error(throwable))

        // when
        val test = quoteUseCase.clearAllQuotes().test()

        //
        // then
        verify(mockQuoteRepository).clearAllQuotes()

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }
}

