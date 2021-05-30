package com.aaron.huey.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.aaron.huey.fragment.ResultFragment

class ResultActivity: SingleFragmentActivity() {
    companion object {
        const val EXTRA_URI = "com.aaron.huey.activity.extra_uri";

        /**
         * Creates a new Intent that can launch this activity with the given uri as parameter
         */
        fun newIntent(context: Context, uri: Uri): Intent {
            val intent = Intent(context, ResultActivity::class.java)
            intent.putExtra(EXTRA_URI, uri)
            return intent
        }
    }

    private var mUri: Uri = Uri.EMPTY


    /**
     * Retrieve the intent extra containing the Uri that will be sent to ResultFragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        val uri: Uri? = intent.getParcelableExtra(EXTRA_URI)
        if (uri!=null)
            mUri = uri

        // call to the super class method
        super.onCreate(savedInstanceState)
    }


    /**
     *  Creates a ResultFragment that will be hosted by this activity
     */
    override fun createFragment(): Fragment {
        return ResultFragment.newInstance(mUri)
    }
}