package com.code.pixabay.ui.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.cachedIn
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import coil.load
import coil.transform.CircleCropTransformation
import com.code.pixabay.R
import com.code.pixabay.data.network.Rest
import com.code.pixabay.data.types.img.ImageItem
import com.code.pixabay.databinding.FragmentViewImageBinding
import com.code.pixabay.domain.downloads.ImageDownloader
import com.code.pixabay.domain.work.DESCRIPTION
import com.code.pixabay.domain.work.TITLE_IMAGE
import com.code.pixabay.domain.work.TYPE_IMAGE
import com.code.pixabay.domain.work.URI_IMAGE
import com.code.pixabay.domain.work.downloadContentWork
import com.code.pixabay.ui.viewmodels.MainVm
import com.code.pixabay.ui.views.pagedList.MediaPagerList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViewImageFragment : Fragment() {

    companion object {
        fun newInstance() = ViewImageFragment()
    }
    lateinit var adapter: MediaPagerList
    lateinit var recyclerView: RecyclerView


    private lateinit var viewModel: MainVm
    lateinit var binding: FragmentViewImageBinding
    private val  onClickItemView:(ImageItem)->Unit =  { imageItem: ImageItem ->
        val dir =ViewImageFragmentDirections.actionViewImageFragmentSelf(imageItem)
        val options = NavOptions.Builder().setEnterAnim(R.anim.slide_in_left).build()
        findNavController().navigate(dir,options)
    }
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val metrix = requireActivity().window.windowManager.currentWindowMetrics
        val displayMetrics = DisplayMetrics()

        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        adapter = MediaPagerList(this.requireContext(),height, width, onClickItemView)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainVm(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args : ViewImageFragmentArgs by navArgs()
        binding = FragmentViewImageBinding.inflate(layoutInflater)
        setupView(args = args)
        setupRelatedImgList(args)
        return binding.root
    }
    private fun setupView(args : ViewImageFragmentArgs){
        val data = args.imageItem
        binding.username.text = data.user
        binding.imgView.apply {
            this.scaleType = ImageView.ScaleType.CENTER_CROP
            load(data.largeImageURL)
        }
        downloadImgSetup(data)
        binding.tags.text = data.tags
        binding.userImg.load(data.userImageURL){
            this.transformations(CircleCropTransformation())
            build()
        }
    }
    fun checkPermission(){
        if(requireContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requireActivity().requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),10)
    }
    private fun downloadImgSetup(imageItem:ImageItem){
        val btn = binding.download
        btn.setOnClickListener {
            checkPermission()
            val data = Data.Builder()

            data.apply {
                putString(URI_IMAGE, imageItem.largeImageURL)
                putString(TITLE_IMAGE,imageItem.user)
                putString(TYPE_IMAGE, ImageDownloader.imageType)
                putString(DESCRIPTION,"")
            }

            val woker = OneTimeWorkRequest.Builder(downloadContentWork::class.java).setInputData(data.build()).build()
            val workManager = WorkManager.getInstance(requireContext())
            workManager.enqueue(woker)

//            val downloader = ImageDownloader(requireContext())
//            downloader.download(imageItem.largeImageURL, imageItem.type, imageItem.user, imageItem.tags,imageItem.user.plus(".jpeg"))
//
//            Toast.makeText(requireContext(), requireContext().filesDir.absolutePath, Toast.LENGTH_SHORT).show()
        }
    }
    private fun setupRelatedImgList(args: ViewImageFragmentArgs){
        recyclerView = binding.relatedImg
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,RecyclerView.VERTICAL)
        val flowData= viewModel.getPager(Rest(requireContext()).pixabayRest,args.imageItem.tags,"all").flow.cachedIn(requireActivity().lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            flowData.collectLatest {
                adapter.submitData(lifecycle,it)
            }
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


}