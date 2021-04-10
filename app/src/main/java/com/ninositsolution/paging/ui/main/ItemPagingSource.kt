package com.ninositsolution.paging.ui.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ninositsolution.paging.data.models.User
import com.ninositsolution.paging.data.repos.MainRepo
import timber.log.Timber
import javax.inject.Inject

class ItemPagingSource @Inject constructor(private val repo: MainRepo) :
    PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        Timber.i("load -> ${params.key}")
        val position = params.key ?: FIRST_PAGE
        return try {
            val response = repo.getUsers(position, LIMIT)
            val nextKey = if ((position + 1) * LIMIT < response.total) position + 1 else null
            LoadResult.Page(
                data = response.users,
                prevKey = if (position == FIRST_PAGE) null else position - 1,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }

    }

    companion object {
        private const val FIRST_PAGE = 0
        private const val LIMIT = 15
    }
}