package com.code.pixabay.data.types.img


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

const val SEARCH_OUTPUT = "SEARCH_OUTPUT"

@JsonClass(generateAdapter = true)
@Entity(tableName = SEARCH_OUTPUT)
data class ImageItem(
    @field:Json(name = "id")
    @field:PrimaryKey
    var id: Int ,
    @field:Json(name = "pageURL")
    var pageURL:String,
    @field:Json(name = "type")
    var type: String,
    @field:Json(name = "tags")
    var tags:String,
    @field:Json(name = "previewURL")
    var previewURL : String,
    @field:Json(name = "previewWidth")
    var previewWidth: Int,
    @field:Json(name = "previewHeight")
    var previewHeight: Int,
    @field:Json(name ="webformatURL" )
    var webformatURL:String,
    @field:Json(name = "webformatWidth")
    var webformatWidth:Int,
    @field:Json(name = "webformatHeight" )
    var webformatHeight:Int,
    @field:Json(name = "largeImageURL")
    var largeImageURL:String,
    @field:Json(name = "imageWidth" )
    var imageWidth:Int,
    @field:Json(name = "imageHeight")
    var imageHeight:Int,
    @field:Json(name = "imageSize" )
    var imageSize:Int,
    @field:Json(name = "views")
    var views:Int,
    @field:Json(name = "downloads")
    var downloads:Int,
    @field:Json(name ="collections")
    var collections: Int ,
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
):Parcelable{
    constructor(p0: Parcel):this(
        p0.createIntArray()!!.get(0),
        p0.createStringArray()!!.get(0),
        p0.createStringArray()!!.get(1),
        p0.createStringArray()!!.get(2),
        p0.createStringArray()!!.get(3),
        p0.createIntArray()!!.get(1),
        p0.createIntArray()!!.get(2),
        p0.createStringArray()!!.get(4),
        p0.createIntArray()!!.get(3),
        p0.createIntArray()!!.get(4),
        p0.createStringArray()!!.get(5),
        p0.createIntArray()!!.get(5),
        p0.createIntArray()!!.get(6),
        p0.createIntArray()!!.get(7),
        p0.createIntArray()!!.get(8),
        p0.createIntArray()!!.get(9),
        p0.createIntArray()!!.get(10),
        p0.createIntArray()!!.get(11),
        p0.createIntArray()!!.get(12),
        p0.createIntArray()!!.get(13),
        p0.createStringArray()!!.get(6),
        p0.createStringArray()!!.get(7)
    ){


    }
    override fun equals(other: Any?): Boolean {
        other as ImageItem
        return this.id == other.id
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {

        val arI=arrayOf(
            id,
            previewWidth,
            previewHeight,
            webformatWidth,
            webformatHeight,
            imageWidth,
            imageHeight,
            imageSize,
            views,
            downloads,
            collections,
            likes,
            comments,
            user_id
        ).toIntArray()
        val arS = arrayOf(
            pageURL,
            type,
            tags,
            previewURL,
            webformatURL,
            largeImageURL,
            user,
            userImageURL
        )
        p0.writeIntArray(arI)
        p0.writeStringArray(arS)

    }


    companion object CREATOR : Parcelable.Creator<ImageItem> {
        override fun createFromParcel(parcel: Parcel): ImageItem {
            return ImageItem(parcel)
        }

        override fun newArray(size: Int): Array<ImageItem?> {
            return arrayOfNulls(size)
        }
    }

}