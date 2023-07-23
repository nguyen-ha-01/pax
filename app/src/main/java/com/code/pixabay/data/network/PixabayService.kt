package com.code.pixabay.data.network

import com.code.pixabay.data.types.img.RestImgSource
import com.code.pixabay.data.types.video.RestVideoSource
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {
    @GET("?key=$key")
    suspend fun getImages(
        @Query("q", encoded = true) query: String,
        @Query("image_type") imageType:String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ):RestImgSource
    @GET("videos/?key=$key")
    suspend fun getVideos(@Query("q",  encoded = true) query : String, @Query("page") page:Int ,  @Query("per_page") perPage : Int ):RestVideoSource
    companion object{
        private  const val  key = "35761633-cb88f6b04eb1ad830784badde"
    }
}