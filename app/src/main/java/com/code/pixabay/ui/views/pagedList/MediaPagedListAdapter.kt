package com.code.pixabay.ui.views.pagedList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.code.pixabay.R
import java.io.File

class DownloadedImgList(val ctx: Context, var onclick: ()->Unit = {}, var list:List<File>):
    RecyclerView.Adapter<DownloadedImgList.Holder>() {
    inner class Holder(view: View):ViewHolder(view){
        init {
            view.setOnClickListener {
                onclick
            }
        }
        val imgV = view.findViewById<ImageView>(R.id.imgItemLoading)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =  LayoutInflater.from(ctx).inflate(R.layout.pager_itemview,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.imgV.load(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }
}