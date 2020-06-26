package com.me.presentation.quote.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.me.domain.quote.entity.QuoteEntity
import com.me.presentation.R
import com.me.presentation.base.adapter.DataBindingAdapter


class QuoteListAdapter : DataBindingAdapter<QuoteEntity>(
    DiffCallback()
) {

    class DiffCallback : DiffUtil.ItemCallback<QuoteEntity>() {
        override fun areItemsTheSame(oldItem: QuoteEntity, newItem: QuoteEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: QuoteEntity, newItem: QuoteEntity): Boolean =
            oldItem == newItem
    }

    override fun getItemViewType(position: Int) = R.layout.quote_item
}