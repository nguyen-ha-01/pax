package com.code.pixabay.data.types.video
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoItem(
    @field:Json(name = "id")
    var id : Int ,
    @field:Json(name = "pageURL")
    var pageURL : String ,
    @field:Json(name = "type")
    var type:String ,
    @field:Json(name = "tags")
    var tags :String,
    @field:Json(name = "duration")
    var duration  : Int ,
    @field:Json(name = "picture_id")
    var picture_id : String,
    @field:Json(name = "videos")
    var videos: Videos,
    @field:Json(name = "views")
    var views:Int,
    @field:Json(name = "downloads")
    var downloads:Int,
    @field:Json(name = "likes")
    var likes:Int,
    @field:Json(name = "comments")
    var comments:Int,
    @field:Json(name = "user_id")
    var user_id:Int,
    @field:Json(name = "user")
    var user:String,
    @field:Json(name = "userImageURL" )
    var userImageURL:String

){
    override fun equals(other: Any?): Boolean {
        val v = other as  VideoItem
        return id ==v.id && videos ==v.videos
    }
}
