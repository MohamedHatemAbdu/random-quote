package com.me.data.quote.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.me.domain.quote.entity.QuoteEntity
import com.squareup.moshi.Json

@Entity(tableName = "quoteData")
data class QuoteData(
    @PrimaryKey
    @field:Json(name = "_id")
    val id: String,

    @field:Json(name = "en")
    val description: String,

    @field:Json(name = "author")
    val author: String
)

fun QuoteData.mapToDomain(): QuoteEntity =
    QuoteEntity(id, description, author)

fun List<QuoteData>.mapToDomain(): List<QuoteEntity> = map { it.mapToDomain() }

fun QuoteEntity.mapToData(): QuoteData =
    QuoteData(
        id, description, author
    )

fun List<QuoteEntity>.mapToData(): List<QuoteData> = map { it.mapToData() }
