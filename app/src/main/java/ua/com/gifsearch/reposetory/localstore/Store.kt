package ua.com.gifsearch.reposetory.localstore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.com.gifsearch.reposetory.retrofit.GifObject
import ua.com.gifsearch.reposetory.retrofit.TenorGif

class Store :ViewModel(){

    companion object{
        private val list_gif : MutableLiveData<TenorGif> = MutableLiveData()
        fun getListGif() = list_gif

        private val list_favorite: MutableLiveData<ArrayList<GifObject>> = MutableLiveData()
        fun  getListFavorite() = list_favorite
    }
}