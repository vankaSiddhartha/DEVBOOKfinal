package com.vanka.devbook

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class UploadFrag : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_upload, container, false)
        var video_intent = view.findViewById<Button>(R.id.up_vid_btn).setOnClickListener{
            startActivity(Intent(requireContext(),UploadVideo::class.java))
        }
        var img_intent = view.findViewById<Button>(R.id.up_dev_shorts).setOnClickListener {
            startActivity(Intent(requireContext(),UploadDevShorts::class.java))
        }
        return view
    }



}