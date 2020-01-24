package ua.com.gifsearch.presenters.search

import androidx.lifecycle.LifecycleOwner
import ua.com.gifsearch.reposetory.retrofit.GifObject

interface ISearch {
    interface Presenter{
        fun onStart()
        fun loadListGif(search: String, queue: Int)
        fun listNext(search: String, queue: Int)
        fun addInFavorite(item: GifObject)
        fun onClickCardView(item: GifObject)
    }
    interface View{
        fun showRecyclerList(list: List<GifObject>)
        fun getLifecycleOwner(): LifecycleOwner
        fun hideKeyBoard()
        fun swohNextListGif(list: List<GifObject>)
        fun finishLoad()
        fun showGif (item : GifObject)
        fun swohMessag(message : String)
    }
}