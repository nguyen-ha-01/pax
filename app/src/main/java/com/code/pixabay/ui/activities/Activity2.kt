package com.code.pixabay.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.paging.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import androidx.work.InputMerger
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.code.pixabay.data.network.Rest
import com.code.pixabay.databinding.Activity2Binding
import com.code.pixabay.domain.work.FindByKeywords
import com.code.pixabay.domain.work.KEY_WORDS
import com.code.pixabay.domain.work.TYPE
import com.code.pixabay.domain.work.imageT
import com.code.pixabay.ui.viewmodels.MainVm
import com.code.pixabay.ui.views.pagedList.MediaPagerList
import kotlinx.coroutines.launch

@UnstableApi class Activity2 : AppCompatActivity() {
    lateinit var binding : Activity2Binding
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val workMng  = WorkManager.getInstance(this)
        val adapter = MediaPagerList(this,1028,1028,{imageItem ->  })
        val vm : MainVm= MainVm(this)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        val rest = Rest(this).pixabayRest

        binding.find.setOnClickListener {
            val dataIn = Data.Builder()
            dataIn.apply {
                putString(KEY_WORDS, binding.keyword.text.toString())
                putString(TYPE, imageT)
            }
            val mergerData = object :InputMerger(){
                override fun merge(inputs: MutableList<Data>): Data {
                    val input = inputs.first()
                    input.getString("EXCEPTION").let {
                        Log.e("EXCEPTION", it?:"nope")
                    }
                    return input

                }
            }
            val searchWork = OneTimeWorkRequest.Builder(FindByKeywords::class.java).setInputData(dataIn.build())
                .setInputMerger(mergerData::class.java).build()
            workMng.enqueue(searchWork)
            lifecycleScope.launch{
                vm.getPager(rest,"","all").liveData.observe(this@Activity2,{
                    adapter.submitData(this@Activity2.lifecycle, it)
                })
//            vm.listData.collectLatest {
//                adapter.submitData(this@Activity2.lifecycle, it)
//                adapter.notifyDataSetChanged()
//            }
            }

        }



    }

}