package com.code.pixabay.data.local_database

import androidx.room.*
import androidx.room.Dao
import com.code.pixabay.data.types.img.ImageItem
import com.code.pixabay.data.types.img.SEARCH_OUTPUT

@Dao
interface Dao {
    @Query("select * from ${MEDIA_PATH_TABLE} limit :page offset :key ")
    fun getData(page: Int , key: Int ):List<MediaPath>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(vararg mediaPath: MediaPath)

    @Delete
    suspend fun delete(mediaPath: MediaPath)

    @Query("select * from ${SEARCH_OUTPUT} limit :page offset :key ")
    fun getSearchOutput(page: Int , key: Int ):List<ImageItem>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImageItem(vararg mediaPath: ImageItem)

    @Query("delete from ${SEARCH_OUTPUT}")
    suspend fun deleteSearchOutput()

}