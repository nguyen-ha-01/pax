package com.code.pixabay.ui.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.code.pixabay.data.local_database.Dao
import com.code.pixabay.data.local_database.MediaPath
import com.code.pixabay.data.network.PixabayService
import com.code.pixabay.data.types.img.ImageItem
import com.code.pixabay.domain.paging.PagedProvider
import com.code.pixabay.domain.paging.paging_src.ImagePagingSrc

class MainVm(@SuppressLint("StaticFieldLeak") val context: Context):ViewModel(){


//    val listData: Flow<PagingData<ImageItem>> =pager.flow.cachedIn(viewModelScope)

    fun getPager(rest:PixabayService,query:String, imageType: String) :Pager<Int, ImageItem> = Pager(PagingConfig(10)){
        PagedProvider(rest, query,imageType)
    }
    fun  getDownloadedImgPager (dao:Dao) =Pager<Int,MediaPath>(PagingConfig(8)){ ImagePagingSrc(dao)}
}