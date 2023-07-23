package com.code.pixabay.domain.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class NotifiSender(val context: Context) {
    val notifiMng = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    enum class Chanel(val id:String,val requestCode: Int){
        Download("10000",1){
                },
        Default("11000",2){
        }
    }
    fun send(chanelId:String,smallIconId:Int,title: String , content :String,intent: Intent?, requestCode: Int ){

        val pendingIntent = if (intent!= null)PendingIntent.getActivity(context,requestCode,intent, PendingIntent.FLAG_UPDATE_CURRENT) else null
        val action  = Notification.Action.Builder(null,null,pendingIntent).build()
        val notification = Notification.Builder(context,chanelId)
            .setContentText(content)
            .setContentTitle(title)
            .setSmallIcon(smallIconId)
            .setActions(action)
            .build()
        notifiMng.notify(1,notification)
    }
}