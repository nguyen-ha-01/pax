package com.code.pixabay.data.types.video

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestVideoSource(
    @field:Json(name = "total")
    var total: Int ,
    @field:Json(name = "totalHits")
    var totalHits: Int ,
    @field:Json(name = "hits")
    var hits: Array<VideoItem>
)
