package com.me.presentation.base.extensions

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


fun SwipeRefreshLayout.startRefreshing() {
    isRefreshing = true
}

fun SwipeRefreshLayout.stopRefreshing() {
    isRefreshing = false
}

