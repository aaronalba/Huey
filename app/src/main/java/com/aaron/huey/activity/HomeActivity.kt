package com.aaron.huey.activity

import androidx.fragment.app.Fragment
import com.aaron.huey.fragment.HomeFragment

class HomeActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return HomeFragment()
    }
}