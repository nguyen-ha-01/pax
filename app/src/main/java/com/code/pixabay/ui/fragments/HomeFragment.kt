package com.code.pixabay.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.code.pixabay.R
import com.code.pixabay.data.network.PixabayService
import com.code.pixabay.data.network.Rest
import com.code.pixabay.data.types.img.ImageItem
import com.code.pixabay.databinding.FragmentHomeBinding
import com.code.pixabay.ui.viewmodels.MainVm
import com.code.pixabay.ui.views.pagedList.MediaPagerList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


// TODO: load image by default ,reload after searching
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    var adapter : MediaPagerList?=  null
    lateinit var vm :MainVm
    lateinit var list: RecyclerView
    lateinit var searchBox: LinearLayout
    lateinit var pixabayService: PixabayService
    private val  onClickItemView:(ImageItem)->Unit =  {imageItem: ImageItem->
        val dir = HomeFragmentDirections.homeToItem(imageItem)
        val options = NavOptions.Builder().setEnterAnim(R.anim.slide_in_left).build()
        findNavController().navigate(dir,options)
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        pixabayService = Rest(context).pixabayRest
        // TODO: i'll replace this vm then get it from host activity
        vm = MainVm(context)
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        adapter = MediaPagerList(this.requireContext(),height, width, onClickItemView)
        // TODO: try to handle backstack 
        requireActivity().supportFragmentManager.addOnBackStackChangedListener {
            Toast.makeText(requireContext(), "onBackStackHandle", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRcyV()
        setupSearchBox()
        binData()
        noNetwork()
        findNavController().saveState()
    }

    private fun noNetwork() {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(cm.activeNetwork == null){

            binding.networkNotification.apply {
                visibility = View.VISIBLE
                text = requireContext().getString(R.string.have_no_internet)
            }
        }
    }


    private fun setupSearchBox() {
        searchBox = binding.searchBox
        searchBox.bringToFront()
        val animationIn  = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_top_to_bot)
        val animationOut = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_bot_to_top)
        list.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy > 20){
                    searchBox.startAnimation(animationOut)
                    lifecycleScope.launch {
                        delay(500)
                        searchBox.translationY = -searchBox.height.toFloat()
                    }

                //                    searchBox.background = requireContext().getDrawable(R.drawable.shape)
                }
                else if(dy< -20){
                    searchBox.startAnimation(animationIn)
                    lifecycleScope.launch {
                        delay(500)
                        searchBox.translationY = 0f
                    }


                }
            }
        })
        onSearchImage()

    }
    private fun onSearchImage(){
        val searchText = binding.inputSearchKey
        binding.searchBtn.setOnClickListener {
            val queryText  =  searchText.text.toString()
            val newPager = vm.getPager(rest = pixabayService, queryText,  "all")
            lifecycleScope.launch {
                newPager.flow.collectLatest {
                    adapter?.submitData(lifecycle,it)
                }
            }

        }
    }

    private fun setupRcyV(){
        list =binding.listImageHome
        list.layoutManager = StaggeredGridLayoutManager(2,RecyclerView.VERTICAL)
        list.adapter = adapter
    }
    fun binData(){
        try{
            val query = if(binding.inputSearchKey.text.isNotEmpty())binding.inputSearchKey.text.toString() else ""
            val pager = vm.getPager(pixabayService,query,"all")
            viewLifecycleOwner.lifecycleScope.launch {
                pager.flow.collectLatest {
                    adapter?.submitData(this@HomeFragment.lifecycle, it)
                }
            }
        }catch (e:Exception){
            Log.e("REST",  e.message.toString())
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        adapter = null
    }

}