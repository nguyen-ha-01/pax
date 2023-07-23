package com.code.pixabay.domain.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.code.pixabay.data.network.Rest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class RestTestingWorker(val context: Context , val params : WorkerParameters):CoroutineWorker(context,params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO){
            val rest  = Rest(context).pixabayRest

            val res = Data.Builder()

                    val getting =this.async {  rest.getVideos("cats", 1,5)}

                    val data = getting.await()
                    delay(500L)
                    res.putInt("numb",data.totalHits)


            return@withContext Result.success(res.build())
        }

    }
}