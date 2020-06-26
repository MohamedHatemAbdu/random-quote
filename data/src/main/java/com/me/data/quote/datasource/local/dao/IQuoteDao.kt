package com.me.data.quote.datasource.local.dao

import androidx.room.*
import com.me.data.quote.model.QuoteData
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface IQuoteDao {

    @Query("Select * from quoteData")
    fun getAllQuotes(): Single<List<QuoteData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllQuotes(quotesList: List<QuoteData>) : List<Long>

    @Query("Select * from quoteData where id = :aId")
    fun getQuote(aId: String): Single<QuoteData>

    @Update
    fun updateQuote(quoteData: QuoteData): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveQuote(quoteData: QuoteData): Long

    @Query("DELETE  FROM quoteData WHERE id = :aId ")
    fun deleteQuote(aId: String): Completable

    @Query("DELETE FROM quoteData")
    fun clear(): Completable
}