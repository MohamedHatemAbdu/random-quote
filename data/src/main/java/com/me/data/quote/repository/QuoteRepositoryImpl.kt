package com.me.data.quote.repository

import com.me.data.quote.datasource.IQuoteCacheDataSource
import com.me.data.quote.datasource.IQuoteRemoteDataSource
import com.me.domain.quote.entity.QuoteEntity
import com.me.domain.quote.repository.IQuoteRepository
import io.reactivex.Completable
import io.reactivex.Single

class QuoteRepositoryImpl(
    val quoteCacheDataSource: IQuoteCacheDataSource,
    val quoteRemoteDataSource: IQuoteRemoteDataSource
) : IQuoteRepository {

    override fun getQuote(refresh: Boolean): Single<QuoteEntity> =
        when (refresh) {

            true -> {
                quoteRemoteDataSource.getRandomQuote().flatMap {
                    quoteCacheDataSource.addQuote(it)
                }
            }
            false -> {
                quoteCacheDataSource.getLatestQuote()
                    .onErrorResumeNext { t: Throwable -> getQuote(true) }
            }
        }

    override fun clearAllQuotes(): Completable =
        quoteCacheDataSource.clearAllQuotes()


}