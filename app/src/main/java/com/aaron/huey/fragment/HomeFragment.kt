package com.aaron.huey.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.aaron.huey.R
import com.aaron.huey.activity.ResultActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

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
    private lateinit var mColorText: TextView
    private var mCapturedPhotos = 1
    private var mFileUri: Uri = Uri.EMPTY

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = layoutInflater.inflate(R.layout.fragment_home, container,false)

        mCameraBtn = view.findViewById(R.id.camera_button)
        mGalleryBtn = view.findViewById(R.id.gallery_button)
        mColorText = view.findViewById(R.id.home_color)

        mCameraBtn.setOnClickListener{ cameraPressed(it) }
        mGalleryBtn.setOnClickListener{ photoPressed(it) }

        return view;
    }

    override fun onDestroy() {
        super.onDestroy()
        deleteCachedImages()
    }


    override fun onResume() {
        super.onResume()
        mColorText.setTextColor(resources.getColor(randomColor(), null))
    }



    // launches an intent to capture a photo using the device's camera app
    private fun cameraPressed(v: View) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // remove old images in the app's private storage
        deleteCachedImages()

        // create file to store the captured image
        val dir: File? = context?.filesDir
        val fileName: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val file: File = File.createTempFile(
                "image${mCapturedPhotos++}_${fileName}",
                ".jpg",
                dir)

        // put the uri of the file to the intent then launch the intent
        if (context!=null && dir!=null) {
            mFileUri = FileProvider.getUriForFile(
                    context!!,
                    "com.aaron.huey.fileprovider",
                    file
            )
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri)
            startActivityForResult(cameraIntent, REQUEST_CAMERA)
        } else
            Toast.makeText(context, "Unable to start the camera!", Toast.LENGTH_LONG).show()
    }

    // launches the device's photo viewer to select a photo
    private fun photoPressed(v: View) {
        val galleryIntent = Intent()
                .setType("image/*")
                .setAction(Intent.ACTION_PICK)

        startActivityForResult(galleryIntent, REQUEST_GALLERY)
    }


    /**
     * Launches the Result Activity with the given uri pointing to the image
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val context = context   // the context that will be used to launch the intents

            when(requestCode) {
                REQUEST_CAMERA -> {
                    if (context!=null) {
                        val intent = ResultActivity.newIntent(context, mFileUri)
                        startActivity(intent)
                    }
                }
                REQUEST_GALLERY -> {
                    val uri: Uri? = data?.data
                    if (uri != null && context != null) {
                        val intent = ResultActivity.newIntent(context, uri)
                        startActivity(intent)
                    }
                }
            }
        }
    }




    /*
        Remove all images in the app's private storage.
     */
    private fun deleteCachedImages() {
        val dir: File? = context?.filesDir
        if (dir != null) {
            val files: Array<String> = dir.list()
            files.iterator().forEach { File(dir, it).delete() }
        }
    }


    /*
        Returns a random material color
     */
    private fun randomColor(): Int {
        val colorList: List<Int> = listOf(
                R.color.red_500,
                R.color.pink_500,
                R.color.purple_500,
                R.color.blue_500,
                R.color.lightBlue_500,
                R.color.cyan_500,
                R.color.teal_500,
                R.color.green_500,
                R.color.lime_500,
                R.color.yellow_500,
                R.color.amber_500,
                R.color.orange_500,
                R.color.deepOrange_500
        )

//        return colorList[(colorList.indices).random()]
        return colorList[12]
    }
}