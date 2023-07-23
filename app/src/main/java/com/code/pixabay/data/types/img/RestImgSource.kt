package com.code.pixabay.data.types.img

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestImgSource(
    @field:Json(name = "total")
    val total:Int,
    @field:Json(name="totalHits")
    val totalHits:Int,
    @field:Json(name = "hits") var hits : Array<ImageItem>
)
