package com.me.data.quote.datasource.remote


import com.me.data.quote.datasource.IQuoteRemoteDataSource
import com.me.data.quote.datasource.remote.api.IQuoteApi
import com.me.data.quote.model.mapToDomain
import com.me.domain.quote.entity.QuoteEntity
import io.reactivex.Single


class QuoteRemoteDataSourceImpl constructor(private val api: IQuoteApi) :
    IQuoteRemoteDataSource {

    override fun getRandomQuote(): Single<QuoteEntity> =
        api.getRandomQuote().map {
            it.mapToDomain()
        }


}