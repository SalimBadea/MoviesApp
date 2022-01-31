package com.creditfins.moviesApp.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.creditfins.moviesApp.helper.MyContextWrapper

abstract class BaseActivity : AppCompatActivity() {
    abstract fun loadLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (loadLayoutResource() != -1)
            setContentView(loadLayoutResource())
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase))
    }

    override fun onResume() {
        super.onResume()
    }
}