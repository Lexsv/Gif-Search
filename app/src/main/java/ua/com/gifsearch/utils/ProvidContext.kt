package ua.com.gifsearch.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

@SuppressLint("Registered")
class ProvidContext : Application() {


    init {
        instance = this
    }

    companion object {
        private var instance: ProvidContext? = null
        fun getContext(): Context = instance!!.applicationContext
    }
}
