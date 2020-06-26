package com.me.presentation.quote.di

import com.me.presentation.quote.view.fragment.QuoteListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class QuoteFragmentBuilder {

    @ContributesAndroidInjector(modules = [ QuoteListModule::class])
    abstract fun bindQuoteListFragment(): QuoteListFragment


}

