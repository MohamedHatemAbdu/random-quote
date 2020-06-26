package com.me.presentation.quote.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.me.domain.quote.usecase.QuoteUseCase
import com.me.presentation.RxSchedulersOverrideRule
import com.me.presentation.base.model.Resource
import com.me.presentation.base.model.ResourceState
import com.me.presentation.quoteEntity
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.*

class QuoteLogViewModelTest {

    private lateinit var viewModel: QuoteViewModel

    private val mockQuoteUseCase: QuoteUseCase = mock(QuoteUseCase::class.java)


    private val id = quoteEntity.id

    private val throwable = Throwable()

    @Rule
    @JvmField
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = QuoteViewModel(mockQuoteUseCase)
    }


    @Test
    fun `get Quote succeeds`() {
        // given
        `when`(mockQuoteUseCase.getRandomQuote(false))
            .thenReturn(Single.just(quoteEntity))

        // when
        viewModel.getRandomQuote(false)

        // then
        verify(mockQuoteUseCase).getRandomQuote(false)
        Assert.assertEquals(
            Resource(ResourceState.SUCCESS, quoteEntity), viewModel.quote.value
        )
    }

    @Test
    fun `get Quote fails`() {
        // given
        `when`(mockQuoteUseCase.getRandomQuote(false))
            .thenReturn(Single.error(throwable))

        // whe
        viewModel.getRandomQuote(false)

        // then
        verify(mockQuoteUseCase).getRandomQuote(false)
        Assert.assertEquals(
            Resource(ResourceState.ERROR, null, throwable.message),
            viewModel.quote.value
        )
    }

}