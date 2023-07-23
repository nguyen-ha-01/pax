package com.code.pixabay.data.types.video

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Videos(
    @field:Json(name=  "large")
    val large: Large,
    @field:Json(name=  "medium")
    val medium: Medium,
    @field:Json(name=  "small")
    val small: Small,
    @field:Json(name=  "tiny")
    val tiny: Tiny
){
    override fun equals(other: Any?): Boolean {
        val data  = other as Videos
        return large == data.large && medium == data.medium && small == data.small && tiny == data.tiny
    }
}
