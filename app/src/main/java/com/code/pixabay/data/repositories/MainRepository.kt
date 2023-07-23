package com.code.pixabay.data.repositories

import com.code.pixabay.data.local_database.Dao
import com.code.pixabay.data.local_database.MediaPath
import com.code.pixabay.data.network.PixabayService

class MainRepository(val dao : Dao,val service: PixabayService) {
    suspend fun daoGetMediaPath(page: Int , key: Int , type: String): List<MediaPath>{
        return dao.getData(page, key)
    }

     suspend fun addMediaPaths(vararg mediaPath: MediaPath) {
        dao.add(*mediaPath)
    }

     suspend fun delete(mediaPath: MediaPath) {
     dao.delete(mediaPath)
    }


}