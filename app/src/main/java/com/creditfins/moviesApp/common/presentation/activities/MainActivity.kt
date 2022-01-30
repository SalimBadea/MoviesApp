package com.creditfins.moviesApp.common.presentation.activities

import android.os.Bundle
import com.creditfins.moviesApp.R
import com.creditfins.moviesApp.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun loadLayoutResource() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}