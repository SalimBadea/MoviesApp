package com.creditfins.moviesApp.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.creditfins.moviesApp.custom.CustomProgressView
import com.creditfins.moviesApp.helper.MyContextWrapper
import com.creditfins.moviesApp.helper.SharedPreferencesManager

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
        SharedPreferencesManager.setLocal(this@BaseActivity)
        SharedPreferencesManager.isLang(true)
    }



    fun unauthenticated(progress: CustomProgressView?) {
        progress?.let { it.showUnauthenticated { openLoginScreen() } }
    }

    fun openLoginScreen() {
//        startActivityForResult(
//            Intent(this, LoginActivity::class.java),
//            UNAUTHORIZED_REQUEST_CODE
//        )
    }
}