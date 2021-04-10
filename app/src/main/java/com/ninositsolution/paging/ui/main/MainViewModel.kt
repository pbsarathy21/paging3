package com.ninositsolution.paging.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ninositsolution.paging.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemPagingSource: ItemPagingSource
) : BaseViewModel() {

    fun getItemsFromAPI() = Pager(
        config = PagingConfig(
            pageSize = 15,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { itemPagingSource }
    ).flow.cachedIn(viewModelScope)

}