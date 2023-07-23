package com.code.pixabay.ui.views.viewpagerA

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.code.pixabay.ui.fragments.DownloadedImageFragment

class PagerAdapter(val fragment: Fragment):FragmentStateAdapter(fragment){
    override fun getItemCount(): Int {
        return 2
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            1->{
                return DownloadedImageFragment()
            }
            else-> return DownloadedImageFragment()
        }
    }
}
