package com.aaron.huey.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.aaron.huey.R

class ResultFragment: Fragment() {
    private lateinit var mResultImage: ImageView
    private lateinit var mColorHolder: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.fragment_result, container, false)

        mResultImage = view.findViewById(R.id.result_image)
        mColorHolder = view.findViewById(R.id.color_holder)

        return view
    }
}