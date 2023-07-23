package com.code.pixabay.ui.views.pagedList

import android.content.Context
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.code.pixabay.R
import com.code.pixabay.data.types.img.ImageItem
import com.code.pixabay.domain.downloads.ImageDownloader

class MediaPagerList(val ctx: Context, val height:Int , val width: Int, var onClickItemView: (ImageItem)->Unit ):PagingDataAdapter<ImageItem, MediaPagerList.MediaListHolder>(
    callback) {
    class MediaListHolder(val view: View): RecyclerView.ViewHolder(view){
        val imgV = view.findViewById<ImageView>(R.id.imgItemLoading)
    }
    var handleOnLongPress:(ImageItem)->Unit = {imageItem->
        try{
            val downloader = ImageDownloader(ctx)
            downloader.download(imageItem.largeImageURL, imageItem.type, imageItem.user, imageItem.tags,imageItem.user.plus(".jpeg"))
            Toast.makeText(ctx, "not crash", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception){

        }

    }
    override fun onBindViewHolder(holder: MediaListHolder, position: Int) {
        val data = getItem(position)!!
        holder.view.setOnClickListener {
            onClickItemView(data)
        }

        holder.view.setOnTouchListener( object :View.OnTouchListener{
            val gestureDetector = GestureDetector(ctx,object :GestureDetector.OnGestureListener{
                override fun onLongPress(p0: MotionEvent) {
                    handleOnLongPress(data)
                }

                override fun onDown(p0: MotionEvent): Boolean {
                    return false
                }

                override fun onShowPress(p0: MotionEvent) {
                    //nothing
                }

                override fun onSingleTapUp(p0: MotionEvent): Boolean {
                    // nothing yet
                    return false
                }

                override fun onScroll(
                    p0: MotionEvent,
                    p1: MotionEvent,
                    p2: Float,
                    p3: Float
                ): Boolean {
                    return false
                }

                override fun onFling(
                    p0: MotionEvent,
                    p1: MotionEvent,
                    p2: Float,
                    p3: Float
                ): Boolean {
                    return false
                }
            })
            override fun onTouch(v: View?, e: MotionEvent?): Boolean {
                if (e != null)gestureDetector.onTouchEvent(e)
                return false
            }
        })
        handleScale(holder, data)
        holder.imgV.load(data.largeImageURL.toUri())
    }
    fun handleScale(holder: MediaListHolder, data: ImageItem){
        val iW = data.imageWidth
        val iH = data.imageHeight
        holder.imgV.apply {
            this.maxHeight = maxWidth*iH/iW
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaListHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.pager_itemview,parent, false)
        return MediaListHolder(view)
    }

    companion object{
        private val callback = object : DiffUtil.ItemCallback<ImageItem>(){
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
                return oldItem==newItem
            }
            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
                return oldItem==newItem
            }
        }
    }
}