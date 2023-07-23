package com.code.pixabay.data.local_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.code.pixabay.data.types.img.ImageItem

@Database(entities = arrayOf(MediaPath::class, ImageItem::class), version = 1, exportSchema = false)
abstract class PathDb :RoomDatabase(){
       abstract fun PathDao():Dao
       companion object{
            @Volatile
           var DB :PathDb? = null
           val db_name = "PATH_DATABASE"
           fun getDb(context: Context):PathDb{
               return DB ?: kotlin.synchronized(this){
                   val _db = Room.databaseBuilder(context, PathDb::class.java, db_name)
                       .allowMainThreadQueries()
                       .build()
                   DB = _db
                    return _db
               }
           }
       }
}