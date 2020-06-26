package com.me.presentation.quote.model

import com.me.domain.quote.entity.QuoteEntity

data class QuoteModel(

    val id: String,

    val description: String,

    val author: String
)


fun QuoteEntity.mapToPresentation(): QuoteModel =
    QuoteModel(
        id,
        description,
        author
    )

fun List<QuoteEntity>.mapToPresentation(): List<QuoteModel> = map { it.mapToPresentation() }
