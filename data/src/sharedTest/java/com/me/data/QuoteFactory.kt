package com.me.data

import com.me.data.DataFactory.Factory.randomUuid
import com.me.data.quote.model.QuoteData

class QuoteFactory {
    object Factory {

        fun makeQuoteData(): QuoteData {
            return QuoteData(randomUuid(), randomUuid(), randomUuid())
        }

        fun makeQuoteData(id: String): QuoteData {
            return QuoteData(
                id,
                randomUuid(),
                randomUuid()
                )
        }


        fun makeQuotesList(count: Int): List<QuoteData> {
            val quotesList = mutableListOf<QuoteData>()
            repeat(count) {
                quotesList.add(makeQuoteData())
            }
            return quotesList
        }

        fun makeQuotesListSameIdsDiffCont(quotesList: List<QuoteData>): List<QuoteData> {
            val copiedQuotesList = mutableListOf<QuoteData>()
            quotesList.map { copiedQuotesList.add(makeQuoteData(it.id)) }
            return copiedQuotesList
        }


    }
}