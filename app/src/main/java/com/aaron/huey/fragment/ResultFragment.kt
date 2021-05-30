package com.aaron.huey.fragment

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.aaron.huey.R

class ResultFragment: Fragment() {
    companion object {
        const val PARAMS_URI = "uri";

        /**
         * Creates an instance of this Fragment with the given uri as Fragment Parameter
         */
        fun newInstance(uri: Uri) : ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putParcelable(PARAMS_URI, uri)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mResultImage: ImageView
    private lateinit var mColorHolder: LinearLayout
    private lateinit var mVibrant: Button
    private lateinit var mLightVibrant: Button
    private lateinit var mDarkVibrant: Button
    private lateinit var mLightMuted: Button
    private lateinit var mDarkMuted: Button

    private var mUri: Uri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // retrieve the uri from the arguments
        val uri: Uri? = arguments?.getParcelable<Uri>(PARAMS_URI)
        if (uri == null) activity?.finish() else mUri = uri
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.fragment_result, container, false)

        mResultImage = view.findViewById(R.id.result_image)
        mColorHolder = view.findViewById(R.id.color_holder)
        mVibrant = view.findViewById(R.id.vibrant)
        mLightVibrant = view.findViewById(R.id.lightVibrant)
        mDarkVibrant = view.findViewById(R.id.darkVibrant)
        mLightMuted = view.findViewById(R.id.lightMuted)
        mDarkMuted = view.findViewById(R.id.darkMuted)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image: Bitmap? = getImage(mUri)
        if (image != null) {
            mResultImage.setImageBitmap(image)

            val palette = Palette.from(image).generate()

            val vibrant: Int = palette.getVibrantColor(-1)
            mVibrant.setBackgroundColor(vibrant)
            mVibrant.text = toHex(vibrant)


            val lightVibrant: Int = palette.getLightVibrantColor(Color.BLACK)
            mLightVibrant.setBackgroundColor(lightVibrant)
            mLightVibrant.text = toHex(vibrant)

            val darkVibrant: Int = palette.getDarkVibrantColor(Color.BLACK)
            mDarkVibrant.setBackgroundColor(darkVibrant)
            mDarkVibrant.text = toHex(darkVibrant)

            val lightMuted: Int = palette.getLightMutedColor(Color.BLACK)
            mLightMuted.setBackgroundColor(lightMuted)
            mLightMuted.text = toHex(lightMuted)

            val darkMuted: Int = palette.getDarkMutedColor(Color.BLACK)
            mDarkMuted.setBackgroundColor(darkMuted)
            mDarkMuted.text = toHex(darkMuted)
        }
    }


    /**
     * Returns a the bitmap of the image given the uri.
     */
    private fun getImage(uri: Uri): Bitmap? {
        val contentResolver: ContentResolver? = activity?.contentResolver

        if (contentResolver != null) {
            if (Build.VERSION.SDK_INT >= 28){
                return MediaStore.Images.Media.getBitmap(contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(contentResolver, uri)
                return ImageDecoder.decodeBitmap(source)
            }
        }
        return null
    }


    /**
     * Converts a color integer to hex string
     */
    private fun toHex(color: Int): String {
        val hex = Integer.toString(color, 16).substring(2)
        return "#$hex"
    }

}