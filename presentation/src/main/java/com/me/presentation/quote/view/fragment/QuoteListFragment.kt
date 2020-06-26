package com.me.presentation.quote.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.me.domain.quote.entity.QuoteEntity
import com.me.presentation.R
import com.me.presentation.base.extensions.startRefreshing
import com.me.presentation.base.extensions.stopRefreshing
import com.me.presentation.base.model.Resource
import com.me.presentation.base.model.ResourceState
import com.me.presentation.base.viewmodel.ViewModelFactory
import com.me.presentation.quote.view.adapter.QuoteListAdapter
import com.me.presentation.quote.view.viewmodel.QuoteViewModel
import dagger.android.support.AndroidSupportInjection
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_quotes_list.*
import javax.inject.Inject


class QuoteListFragment : Fragment() {


    @Inject
    lateinit var adapter: QuoteListAdapter

    @Inject
    lateinit var vmFactory: ViewModelFactory<QuoteViewModel>

    private val vm by lazy {
        ViewModelProviders.of(this, vmFactory)
            .get(QuoteViewModel::class.java)
    }

    private val snackBar by lazy {
        Snackbar.make(
            srlQuoteList,
            getString(R.string.quotes_list_no_cached_data),
            Snackbar.LENGTH_INDEFINITE
        )
    }


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_quotes_list, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            vm.getRandomQuote(false)
        }

        rvQuoteList.adapter = adapter
        rvQuoteList.itemAnimator = SlideInUpAnimator(OvershootInterpolator(3f))

        vm.quote.observe(this, Observer { updateQuote(it) })

        srlQuoteList.setOnRefreshListener {
            vm.getRandomQuote(true)
        }

    }


    private fun updateQuote(resource: Resource<QuoteEntity>?) {
        resource?.let {
            updateLoadingState(it.state)

            it.data?.let {quote ->
                adapter.submitList(listOf(quote))
            }
            it.message?.let { showSnackBar(it) }
        }
    }

    private fun updateLoadingState(state: ResourceState) {
        when (state) {
            ResourceState.LOADING -> srlQuoteList.startRefreshing()
            ResourceState.SUCCESS -> srlQuoteList.stopRefreshing()
            ResourceState.ERROR -> srlQuoteList.stopRefreshing()
        }
    }

    private fun showSnackBar(msg: String) {
        snackBar.setAction(getString(R.string.quote_list_snack_bar_action)) {
            snackBar.dismiss()
        }.setText(msg).show()
    }
}
