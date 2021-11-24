package com.datikaa.lorempicsum.feature.main_pager.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.datikaa.lorempicsum.domain.data.PicsumPicture
import com.datikaa.lorempicsum.extension.toDomainModel
import com.datikaa.lorempicsum.network.PicsumService

class MainPagerPagingSource(
    private val picsumService: PicsumService
) : PagingSource<Int, PicsumPicture>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PicsumPicture> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val loadSize = params.loadSize
            val response = picsumService.list(nextPageNumber, loadSize)
            LoadResult.Page(
                data = response.map { it.toDomainModel() },
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PicsumPicture>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}