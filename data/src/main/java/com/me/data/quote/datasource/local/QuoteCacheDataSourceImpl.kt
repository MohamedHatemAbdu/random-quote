package com.me.data.quote.datasource.local


import com.me.data.base.AppDatabase
import com.me.data.quote.datasource.IQuoteCacheDataSource
import com.me.data.quote.datasource.local.dao.IQuoteDao
import com.me.data.quote.model.mapToData
import com.me.data.quote.model.mapToDomain
import com.me.domain.quote.entity.QuoteEntity
import io.reactivex.Completable
import io.reactivex.Single


class QuoteCacheDataSourceImpl(private val database: AppDatabase) : IQuoteCacheDataSource {

    private val dao: IQuoteDao = database.getQuoteDao()

    override fun getQuotes(): Single<List<QuoteEntity>> =
        dao.getAllQuotes().map { it.mapToDomain() }

    override fun setQuotes(quotesList: List<QuoteEntity>): Single<List<QuoteEntity>> {
        dao.clear()
        dao.saveAllQuotes(quotesList.mapToData())
        return getQuotes()
    }

    override fun getQuote(id: String): Single<QuoteEntity> =
        dao.getQuote(id).map {
            it.mapToDomain()
        }

    override fun getLatestQuote(): Single<QuoteEntity> =
        dao.getAllQuotes().map {
            if (it.isEmpty())
                throw Exception("No Cached Item")
            else
                it[it.size - 1].mapToDomain()
        }


    override fun addQuote(quote: QuoteEntity): Single<QuoteEntity> {
        val id = quote.id
        dao.saveQuote(quote.mapToData())
        return dao.getQuote(id).map { it.mapToDomain() }
    }

    override fun editQuote(quote: QuoteEntity): Completable =
        dao.updateQuote(quote.mapToData())

    override fun deleteQuote(id: String): Completable =
        dao.deleteQuote(id)

    override fun clearAllQuotes(): Completable =
        dao.clear()


}