package com.code.pixabay.domain.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.code.pixabay.data.local_database.PathDb
import com.code.pixabay.data.network.Rest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

const val KEY_WORDS = "FIND"
const val LOADING_SIZE = "LOADING_SIZE"
const val TYPE = "TYPE"
const val imageT = "IMAGE"
const val videoT = "VIDEO"
const val PAGE = "PAGE"
class FindByKeywords(val context : Context, val params : WorkerParameters):CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return   withContext(Dispatchers.IO){
            val keywords = inputData.getString(KEY_WORDS) ?: ""
            val type  = inputData.getString(TYPE) ?: imageT
            val page_size = inputData.getInt(LOADING_SIZE, 100)
            val page = inputData.getInt(PAGE,  1)
            val rest = Rest(context).pixabayRest
            val dataout = Data.Builder()
            val db= PathDb.getDb(context)
            val dao = db.PathDao()
            // TODO: check it
            when(type){
                imageT-> {val data = async { rest.getImages(keywords, "all",page,page_size)}.await()
                    delay(200L)
                    try{
                        data.hits.forEach {
                            dao.addImageItem(it)
                        }
                    }catch(e:Exception){
                        val TAG = "wtf"
                        Log.wtf(TAG, "doWork: ${e.message}")
                    }
                    dataout.putInt("countImage",  data.totalHits)
                }
                videoT->{val data = async { rest.getVideos(keywords, page,page_size)}.await()
                    delay(200L)
                    dataout.putInt("videos", data.totalHits)
                }

                else-> {
                    return@withContext Result.retry()
                }
            }
            dataout.putString("EXCEPTION","EXCEPTION")
            return@withContext Result.failure(dataout.build())
        }
    }
}