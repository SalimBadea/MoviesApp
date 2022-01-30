package com.creditfins.moviesApp

import androidx.multidex.MultiDexApplication
import com.facebook.FacebookSdk
import com.creditfins.moviesApp.helper.SharedPreferencesManager.init
import com.creditfins.moviesApp.di.modules
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoviesAppApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
        init(applicationContext)
        startKoin {
            androidContext(this@MoviesAppApplication)
            androidLogger()
            modules(modules)
        }
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("font/Cairo-Regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                ).build()
        );
    }
}