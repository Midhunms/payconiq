package com.payconiq.app

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class PayconiqApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: PayconiqApplication? = null

        fun applicationContext() : Context? {
            return instance?.applicationContext
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }


    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }

}

