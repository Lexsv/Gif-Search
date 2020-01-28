package ua.com.gifsearch.utils

import android.content.Context

import android.net.ConnectivityManager

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


fun isNet(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkRequest = cm.activeNetwork
    val capabilities = cm.getNetworkCapabilities(networkRequest)
    return capabilities != null

}

fun resourceToSring(resource: Int) = ProviderContext.getContext().resources.getString(resource)


inline fun <reified T> genericType(): Type = object: TypeToken<T>() {}.type