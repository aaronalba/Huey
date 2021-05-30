package com.aaron.huey.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(context, "result fragment ${mUri}", Toast.LENGTH_LONG).show()
    }
}