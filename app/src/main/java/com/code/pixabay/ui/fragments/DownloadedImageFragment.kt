package com.code.pixabay.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.code.pixabay.R
import com.code.pixabay.databinding.FragmentDownloadedImageBinding

class DownloadedImageFragment : Fragment() {
    lateinit var binding: FragmentDownloadedImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadedImageBinding.bind(inflater.inflate(R.layout.fragment_downloaded_image, container, false))

        return binding.root
    }

    companion object {

    }
}