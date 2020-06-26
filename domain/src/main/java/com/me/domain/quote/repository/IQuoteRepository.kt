package com.me.domain.quote.repository

import com.me.domain.quote.entity.QuoteEntity
import io.reactivex.Completable
import io.reactivex.Single


interface IQuoteRepository {

    fun getQuote(refresh: Boolean): Single<QuoteEntity>

    fun clearAllQuotes(): Completable

}