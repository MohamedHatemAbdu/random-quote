package com.me.data.quote.model

import com.me.data.quoteData
import org.junit.Test

class QuoteDataTest {


    @Test
    fun `map data to domain`() {
        // given

        // when
        val model = quoteData.mapToDomain()

        // then
        assert(model.id == quoteData.id)
        assert(model.description == quoteData.description)
        assert(model.author == quoteData.author)
    }
}