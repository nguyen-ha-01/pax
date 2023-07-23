package com.code.pixabay.domain.work

import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.code.pixabay.R
import com.code.pixabay.data.local_database.MediaPath
import com.code.pixabay.data.local_database.PathDb
import com.code.pixabay.domain.downloads.ImageDownloader
import com.code.pixabay.domain.downloads.ImageDownloader.Companion.NAME_DIR
import com.code.pixabay.domain.notification.NotifiSender
import com.code.pixabay.ui.activities.MainActivity
import java.io.File
import java.util.Calendar

const val URI_IMAGE = "uri_of_image"
const val TITLE_IMAGE = "TITLE_OF_IMAGE"
const val DESCRIPTION = "DESCRIPTION"
const val TYPE_IMAGE = "TYPE_IMAGE"
const val NAME_IMAGE = "IMAGE_NAME"
class downloadContentWork(val context: Context, val params: WorkerParameters):CoroutineWorker(context,  params) {
    override suspend fun doWork(): Result {
        //get uri

        val uri = inputData.getString(URI_IMAGE)
        val type = inputData.getString(TYPE_IMAGE) ?: ImageDownloader.imageType
        val title = inputData.getString(TITLE_IMAGE)
        val des = inputData.getString(DESCRIPTION)
        val calender = Calendar.getInstance()

        val imageName = "image${title}${calender.time}.jpeg"
        var result = 0L
        val notifiSender = NotifiSender(context = context)
        if(uri != null){
            val downloader = ImageDownloader(context)
            result = downloader.download(uri,type , title, des,  imageName)
            val output = Data.Builder().apply {
                putLong("requestID",  result)
            }.build()
            val dir = File(context.getExternalFilesDir(null), NAME_DIR)
            val _uri = File(dir,imageName ).path
            val db = PathDb.getDb(context)

            db.PathDao().add(MediaPath(uri,_uri,ImageDownloader.imageType ))
            val intent = Intent(context,MainActivity::class.java)
            notifiSender.send(NotifiSender.Chanel.Download.id, R.drawable.home, "Downloaded", "$imageName is downloaded",intent, 1)
            return Result.success(output)
        }
        else{notifiSender.send(NotifiSender.Chanel.Download.id, R.drawable.home, "Downloaded", "$imageName cannot be downloaded", null, 1); return Result.failure()}

    }


    override suspend fun getForegroundInfo(): ForegroundInfo {
//        val info = ForegroundInfo()
        return super.getForegroundInfo()
    }
}