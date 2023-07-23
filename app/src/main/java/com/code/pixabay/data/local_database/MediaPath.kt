package com.code.pixabay.data.local_database

import androidx.room.Entity
import androidx.room.PrimaryKey
const val MEDIA_PATH_TABLE = "MEDIA_PATH_TABLE"
@Entity(tableName = MEDIA_PATH_TABLE)
class MediaPath(@PrimaryKey val id: String, val pathName:String,val type : String)  {
    override fun equals(other: Any?): Boolean {
        val p2 = other as MediaPath
        return id==p2.id&& pathName== p2.pathName
    }
}