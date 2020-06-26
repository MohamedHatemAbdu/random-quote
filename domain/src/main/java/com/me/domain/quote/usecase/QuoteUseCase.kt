package com.me.domain.quote.usecase


import com.me.domain.quote.entity.QuoteEntity
import com.me.domain.quote.repository.IQuoteRepository
import io.reactivex.Completable
import io.reactivex.Single


class QuoteUseCase constructor(
    private val quoteRepository: IQuoteRepository
) {

    fun getRandomQuote(refresh: Boolean): Single<QuoteEntity> =
        quoteRepository.getQuote(refresh)


    fun clearAllQuotes(): Completable =
        quoteRepository.clearAllQuotes()


}


