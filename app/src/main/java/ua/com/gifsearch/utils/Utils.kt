package ua.com.gifsearch.utils

import android.content.Context

import android.net.ConnectivityManager

import com.google.gson.reflect.TypeToken



fun isNet(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkRequest = cm.activeNetwork
    val capabilities = cm.getNetworkCapabilities(networkRequest)
    return capabilities != null

}




inline fun <reified T> genericType() = object: TypeToken<T>() {}.type