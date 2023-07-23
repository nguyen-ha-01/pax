package com.code.pixabay.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.code.pixabay.data.network.PixabayService
import com.code.pixabay.data.types.img.ImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PagedProvider(val service: PixabayService, val query:String,val imageType: String):PagingSource<Int,ImageItem>() {
    override fun getRefreshKey(state: PagingState<Int, ImageItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageItem> {
        return withContext(Dispatchers.IO){
            val key =params.key ?:1
            val size = params.loadSize
            try {
                val data =  service.getImages(query,imageType, key, size)
                val prevKey = if(key ==0 && key > size) null else key-size
                val nextKey = if (data.hits.size < size) null else key+size
                LoadResult.Page(data.hits.toList(),prevKey, nextKey)
            }catch (e:Exception){
                LoadResult.Error(Throwable())
            }
        }

    }
}