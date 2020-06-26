package com.me.data.quote.datasource

import com.me.domain.quote.entity.QuoteEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IQuoteCacheDataSource {

    fun getQuotes(): Single<List<QuoteEntity>>

    fun setQuotes(quotesList: List<QuoteEntity>): Single<List<QuoteEntity>>

    fun getQuote(id: String): Single<QuoteEntity>

    fun getLatestQuote(): Single<QuoteEntity>

    fun addQuote(quote: QuoteEntity): Single<QuoteEntity>

    fun editQuote(quote: QuoteEntity): Completable

    fun deleteQuote(id: String): Completable

    fun clearAllQuotes(): Completable


}