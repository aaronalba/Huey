package com.aaron.huey.activity

import androidx.fragment.app.Fragment
import com.aaron.huey.fragment.ResultFragment

class ResultActivity: SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return ResultFragment()
    }
}