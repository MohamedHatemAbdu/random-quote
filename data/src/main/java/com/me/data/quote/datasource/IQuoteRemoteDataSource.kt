package com.me.data.quote.datasource

import com.me.domain.quote.entity.QuoteEntity
import io.reactivex.Single


interface IQuoteRemoteDataSource {

    fun getRandomQuote(): Single<QuoteEntity>
}