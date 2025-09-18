package com.zs.my_ecommerce.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.atomic.AtomicInteger

object AppGlobals {
    private val _loadingState = MutableStateFlow(GlobalLoadingState.IDLE)
    val loadingState: StateFlow<GlobalLoadingState> = _loadingState


    private val loadingCounter = AtomicInteger(0)

    fun showGlobalLoading() {
        if (loadingCounter.getAndIncrement() == 0) {
            _loadingState.value = GlobalLoadingState.LOADING
        }
    }

    fun hideGlobalLoading() {
        if (loadingCounter.decrementAndGet() <= 0) {
            loadingCounter.set(0)
            _loadingState.value = GlobalLoadingState.IDLE
        }
    }

    fun showPagingLoadingMore() {
        _loadingState.value = GlobalLoadingState.PAGING_LOADING_MORE
    }
}