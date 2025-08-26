package com.ersurajrajput.wavesoffood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.ersurajrajput.wavesoffood.R
import com.ersurajrajput.wavesoffood.databinding.FragmentHomeBinding

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.img_banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.img_banner2,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.img_banner3,ScaleTypes.FIT))

        binding.imageSlider.setImageList(imageList)



    }



    companion object {

    }
}