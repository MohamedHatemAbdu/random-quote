package com.me.presentation.quote.di


import com.me.data.base.AppDatabase
import com.me.data.base.ServiceGenerator
import com.me.data.quote.datasource.IQuoteCacheDataSource
import com.me.data.quote.datasource.IQuoteRemoteDataSource
import com.me.data.quote.datasource.local.QuoteCacheDataSourceImpl
import com.me.data.quote.datasource.remote.QuoteRemoteDataSourceImpl
import com.me.data.quote.datasource.remote.api.IQuoteApi
import com.me.data.quote.repository.QuoteRepositoryImpl
import com.me.domain.quote.repository.IQuoteRepository
import com.me.domain.quote.usecase.QuoteUseCase
import com.me.presentation.BuildConfig
import com.me.presentation.quote.view.adapter.QuoteListAdapter
import com.me.presentation.quote.view.viewmodel.QuoteViewModel
import dagger.Module
import dagger.Provides


@Module
class QuoteListModule {

    @Provides
    fun provideQuoteServiceAPIs(): IQuoteApi =
        ServiceGenerator.createService(IQuoteApi::class.java, BuildConfig.DEBUG)


    @Provides
    fun provideQuoteRemoteDataSource(
        quoteApi: IQuoteApi
    ): IQuoteRemoteDataSource =
        QuoteRemoteDataSourceImpl(quoteApi)


    @Provides
    fun provideQuoteCacheDataSource(
        appDatabase: AppDatabase
    ): IQuoteCacheDataSource =
        QuoteCacheDataSourceImpl(appDatabase)


    @Provides
    fun provideQuoteRepository(
        quoteRemoteDataSource: IQuoteRemoteDataSource, quoteCacheDataSource: IQuoteCacheDataSource
    ): IQuoteRepository =
        QuoteRepositoryImpl(
            quoteCacheDataSource,
            quoteRemoteDataSource
        )


    @Provides
    fun provideQuoteUseCase(quoteRepository: IQuoteRepository): QuoteUseCase =
        QuoteUseCase(quoteRepository)

    @Provides
    fun provideQuoteListViewModel(
        quoteUseCase: QuoteUseCase
    ): QuoteViewModel =
        QuoteViewModel(
            quoteUseCase
        )

    @Provides
    fun provideQuoteListAdapter(): QuoteListAdapter {
        return QuoteListAdapter()
    }



}