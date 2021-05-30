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
        mCameraBtn.setOnClickListener{ cameraPressed(it) }
        mGalleryBtn.setOnClickListener{ photoPressed(it) }

        return view;
    }

    override fun onDestroy() {
        super.onDestroy()
        deleteCachedImages()
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
     * Launches the ColorAnalyzer Activity (to be implemented) with the given image
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_CAMERA -> {
                    Toast.makeText(context, "image captured! ${mFileUri}", Toast.LENGTH_LONG).show()
                    val context = context
                    if (context!=null) {
                        val intent = ResultActivity.newIntent(context, mFileUri)
                        startActivity(intent)
                    }
                }
                REQUEST_GALLERY -> {
                    Toast.makeText(context, "image chosen!", Toast.LENGTH_SHORT).show()
                    val uri = data?.data

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
}