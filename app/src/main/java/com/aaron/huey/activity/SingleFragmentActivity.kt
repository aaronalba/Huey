package com.aaron.huey.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aaron.huey.R

/**
 * Template class for creating activities that can host a single fragment.
 * @author Aaron Alba
 */
abstract class SingleFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.acitivity_fragment)

        // check if a fragment has been hosted already
        var fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            // if no fragment is hosted yet, host the fragment from createFragment method
            fragment = createFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    /**
     * Defines the fragment that will be hosted by this activity
     */
    protected abstract fun createFragment() : Fragment;
}