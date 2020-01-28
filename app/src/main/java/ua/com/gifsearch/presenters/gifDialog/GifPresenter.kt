package ua.com.gifsearch.presenters.gifDialog

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment

import ua.com.gifsearch.reposetory.retrofit.GifObject

import ua.com.gifsearch.utils.ProviderContext
import ua.com.gifsearch.utils.isNet


import javax.inject.Inject

class GifPresenter @Inject constructor(var view: IGifDialog.View) : IGifDialog.Presenter{


    override fun onStart(item: GifObject) {
        var title = item.title
        if (item.title == "")title="No name"
       view.viewGif(title,item.media[0].gif.url)

    }

    override fun onSave(item: GifObject) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if ( ProviderContext.getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                view.requestPermission()

            }else{
                startDownload(item)
            }
        }else{
            startDownload(item)

        }


    }

    private fun startDownload(item: GifObject) {
        if (isNet(ProviderContext.getContext())) {
            val url = item.media[0].gif.url
            val request = DownloadManager.Request(Uri.parse(url))
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI )
                request.setTitle("Загрузка Gif")
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"/gif/${System.currentTimeMillis()}.gif")
            val manager = ProviderContext.getContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                manager.enqueue(request)
            view.dismissView("Gif сохранено в: \n Download/gif/")

        } else view.showMessage("Подключите Интернет")
    }


}