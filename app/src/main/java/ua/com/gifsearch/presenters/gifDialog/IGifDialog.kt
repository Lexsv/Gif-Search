package ua.com.gifsearch.presenters.gifDialog

import androidx.lifecycle.LifecycleOwner
import ua.com.gifsearch.reposetory.retrofit.GifObject

interface IGifDialog {
    interface View{
        fun getLifecycleOwner(): LifecycleOwner
        fun viewGif(title: String, url: String)
        fun requestPermission()
        fun dismissView(message : String)
        fun swohMessag(message : String)
    }
    interface Presenter{
        fun onStart(item: GifObject)
        fun onSave(item: GifObject)


    }
}
