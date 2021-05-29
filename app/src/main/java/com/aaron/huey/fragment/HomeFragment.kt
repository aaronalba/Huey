package com.aaron.huey.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aaron.huey.R

/**
 * Fragment that will show the screen to pick
 * whether the image will come from the camera or the gallery.
 * @author Aaron Alba
 */
class HomeFragment: Fragment() {
    companion object {
        const val REQUEST_CAMERA = 1
        const val REQUEST_GALLERY = 2
    }

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
        mCameraBtn.setOnClickListener{ cameraPressed(it) }
        mGalleryBtn.setOnClickListener{ photoPressed(it) }

        return view;
    }

    // launches an intent to capture a photo using the device's camera app
    private fun cameraPressed(v: View) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA)
    }

    // launches the device's photo viewer to select a photo
    private fun photoPressed(v: View) {
        val galleryIntent = Intent()
                .setType("image/*")
                .setAction(Intent.ACTION_PICK)

        startActivityForResult(galleryIntent, REQUEST_GALLERY)
    }


    /**
     * Launches the ColorAnalyzer Activity (to be implemented) with the given image
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_CAMERA -> {
                    Toast.makeText(context, "image captured!", Toast.LENGTH_SHORT).show()
                }
                REQUEST_GALLERY -> {
                    Toast.makeText(context, "image chosen!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}