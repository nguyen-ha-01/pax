package com.code.pixabay.data.types.combine

import com.code.pixabay.data.types.img.ImageItem
import com.code.pixabay.data.types.video.VideoItem
const val imageType="i"
const val videoType ="v"
data class MediaItem(val id: String, val link:String, val width:Int, val height:Int, val type: String){

    fun ImageItem.toMediaItem():MediaItem{
        this.apply {
            return MediaItem(id.toString(),webformatURL, webformatWidth,webformatHeight, imageType )
        }
    }
    fun VideoItem.toMediaItem():MediaItem{
        this.apply {
            return MediaItem(id.toString() , videos.tiny.url, videos.tiny.width, videos.tiny.height, videoType)
        }
    }
}
