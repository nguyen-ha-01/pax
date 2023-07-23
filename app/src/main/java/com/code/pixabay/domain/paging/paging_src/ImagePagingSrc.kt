package com.code.pixabay.domain.paging.paging_src

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.code.pixabay.data.local_database.Dao
import com.code.pixabay.data.local_database.MediaPath
//
class ImagePagingSrc(val dao : Dao): PagingSource<Int, MediaPath>() {
    override fun getRefreshKey(state: PagingState<Int, MediaPath>): Int? {
        return  state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaPath> {
        val per_page = params.loadSize
        val page  = params.key ?:0

        try{
            val data = dao.getData(per_page,page)
            val nextKey = if(data.size < per_page) null else page+per_page
            val prevKey =  if(page==0) null else page-per_page
            return LoadResult.Page(data.toList(),prevKey,nextKey)
        }catch (e: Exception){
            return LoadResult.Error(object : Throwable("no hope"){})
        }
    }
}