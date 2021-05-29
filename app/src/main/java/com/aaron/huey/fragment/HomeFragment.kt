package com.aaron.huey.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.aaron.huey.R

/**
 * Fragment that will show the screen to pick
 * whether the image will come from the camera or the gallery.
 * @author Aaron Alba
 */
class HomeFragment: Fragment() {
    private lateinit var mCameraBtn: Button
    private lateinit var mGalleryBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = layoutInflater.inflate(R.layout.fragment_home, container,false)

        mCameraBtn = view.findViewById(R.id.camera_button)
        mGalleryBtn = view.findViewById(R.id.gallery_button)

        return view;
    }
}