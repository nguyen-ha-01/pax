package com.code.pixabay.domain.paging.paging_src

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.code.pixabay.data.network.PixabayService
import com.code.pixabay.data.types.video.VideoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class RestVideoPagingSrc( val scope:CoroutineScope, val pixabayService: PixabayService,val query : String): PagingSource<Int, VideoItem>() {
    override fun getRefreshKey(state: PagingState<Int, VideoItem>): Int? {
        return  state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoItem> {
        val per_page = params.loadSize
        val page  = params.key ?:0

            val data = scope.async {  pixabayService.getVideos(query,page ,per_page )}.await()
            val nextKey = if(data.hits.size < per_page) null else page+per_page
            val prevKey =  if(page==0) null else page-per_page
            return LoadResult.Page(data.hits.toList(),prevKey,nextKey)

    }
}