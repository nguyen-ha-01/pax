package com.code.pixabay.domain.downloads

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ImageDownloadedReceiver(): BroadcastReceiver() {
    override fun onReceive(ctx: Context?, intent: Intent?) {
        if(intent?.action != "android.intent.action.DOWNLOAD_COMPLETE"){
            Log.i("download", "downloaded")
        }
    }
}