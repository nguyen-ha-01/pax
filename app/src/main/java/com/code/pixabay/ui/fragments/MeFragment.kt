package com.code.pixabay.ui.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.code.pixabay.data.local_database.PathDb
import com.code.pixabay.databinding.FragmentMeBinding
import com.code.pixabay.domain.downloads.ImageDownloader
import com.code.pixabay.ui.viewmodels.MainVm
import com.code.pixabay.ui.views.pagedList.DownloadedImgList
import java.io.File

const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 111

class MeFragment : Fragment() {
    lateinit var vm :MainVm
    lateinit var list : MutableList<String>
    lateinit var binding: FragmentMeBinding
    lateinit var _adapter: DownloadedImgList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = MainVm(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMeBinding.inflate(layoutInflater)

        loadLocalData()
        return binding.root
    }
    private fun  setupList(){

        val dao = PathDb.getDb(requireContext()).PathDao()


        binding.listImgDownloaded.apply {
            adapter = _adapter
            layoutManager =  StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }
    }

    @SuppressLint("Recycle", "Range")
    private fun content(){
        list = mutableListOf()
        val dir = File(requireContext().getExternalFilesDir(null), ImageDownloader.NAME_DIR)
        val listFiles = dir.listFiles()
        _adapter =DownloadedImgList(requireContext(),{}, listFiles.toList())
    }
    fun loadLocalData(){

        if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
        } else {
            content()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()

    }



    companion object {

    }
}