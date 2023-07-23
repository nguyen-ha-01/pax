package com.code.pixabay.domain.downloads

import android.app.DownloadManager
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.net.toUri
import java.io.File


class ImageDownloader (val context: Context):Downloader {
    val downloadMng  =
        context.getSystemService(DownloadManager::class.java)

    val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun networkChecking():Int{
        var isWifiConn = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return if(isWifiConn?.isConnected == true) wifi else mobile

    }
    fun createDirIfNotExists(path: String):File  {

        val file = File(context.getExternalFilesDir(null), path)

        if (!file.exists()) {
           file.mkdir()
        }
        return file
    }



    override fun download(url: String, type: String,title : String?,decription: String? , fileName : String): Long {
        val networkType = networkChecking()
        val dir = createDirIfNotExists(NAME_DIR)
//        val dir = File(context.getExternalFilesDir(null), NAME_DIR)
        val file = File(dir,fileName)
        val request = DownloadManager.Request(url.toUri())
            .setMimeType(type)
            .setAllowedNetworkTypes(networkType)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setTitle(title)
            .setDescription(decription)
            .setDestinationUri(file.toUri())

        return downloadMng.enqueue(request)
    }
    companion object{
        val  mobile = DownloadManager.Request.NETWORK_MOBILE
        val wifi  =  DownloadManager.Request.NETWORK_WIFI
        val imageType : String= "image/jpeg"
        const val NAME_DIR = "IMAGES"

    }
}