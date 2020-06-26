package com.me.data.quote.datasource.remote.api

import com.me.data.quote.model.QuoteData
import io.reactivex.Single
import retrofit2.http.GET

interface IQuoteApi {

    @GET("/quotes/random")
    fun getRandomQuote(): Single<QuoteData>

}
