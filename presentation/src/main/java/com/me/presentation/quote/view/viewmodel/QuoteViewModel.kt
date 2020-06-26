package com.me.presentation.quote.view.viewmodel

import androidx.lifecycle.MutableLiveData
import com.me.domain.quote.entity.QuoteEntity
import com.me.domain.quote.usecase.QuoteUseCase
import com.me.presentation.base.extensions.setError
import com.me.presentation.base.extensions.setLoading
import com.me.presentation.base.extensions.setSuccess
import com.me.presentation.base.model.Resource
import com.me.presentation.base.viewmodel.BaseViewModel
import io.reactivex.schedulers.Schedulers

class QuoteViewModel constructor(private val quoteUseCase: QuoteUseCase) :
    BaseViewModel() {

    val quote = MutableLiveData<Resource<QuoteEntity>>()

    fun getRandomQuote(refresh: Boolean) =
        compositeDisposable.add(
            quoteUseCase.getRandomQuote(refresh)
                .doOnSubscribe { quote.setLoading() }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    quote.setSuccess(it)
                }, {
                    quote.setError(it.message)
                })
        )




    fun clearAll() {
        compositeDisposable.add(
            quoteUseCase.clearAllQuotes(
            ).subscribeOn(Schedulers.io())
                .subscribe({}, {})
        )
    }


}