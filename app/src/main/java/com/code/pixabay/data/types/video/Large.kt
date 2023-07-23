package com.code.pixabay.data.types.video

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Large(
    @field:Json(name ="url")
    val url :String ,
    @field:Json(name = "width")
    val width: Int,
    @field:Json(name = "height")
    val height: Int ,
    @field:Json(name = "size")
    val size : Int
) {
    override fun equals(other: Any?): Boolean {
        val data = other as Large
        return url==data.url && width == data.width &&height ==data.height && size == data.size
    }
}
