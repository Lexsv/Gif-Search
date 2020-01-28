package ua.com.gifsearch.presenters.mainActivity

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ua.com.gifsearch.reposetory.localstore.Store
import ua.com.gifsearch.reposetory.retrofit.GifObject
import ua.com.gifsearch.reposetory.room.FavoriteDB
import ua.com.gifsearch.reposetory.room.FavoriteList
import ua.com.gifsearch.utils.ProviderContext
import ua.com.gifsearch.utils.genericType
import javax.inject.Inject

class MainPresenter @Inject constructor(var view: IMaimActivity.View): IMaimActivity.Presenter {


    override fun onStart() {
        val result = GlobalScope.async {
            FavoriteDB.getInit(ProviderContext.getContext())
                      .getFavorite()
                      .getAll()
        }
        GlobalScope.launch {
            val list = result.await()

            if (list.isNotEmpty()){
                val turnsType = genericType<ArrayList<GifObject>>()

                val ob = Gson().fromJson<ArrayList<GifObject>>(list[0].json,turnsType)
                Store.getListFavorite().postValue(ob)
            }
        }
    }

    override fun onSave() {
        val result = GlobalScope.async {
            FavoriteDB.getInit(ProviderContext.getContext())
                      .getFavorite()
                      .getAll()
        }

        GlobalScope.launch {
            val listSave = Gson().toJson(Store.getListFavorite().value)

            if (result.await().isEmpty()){
                FavoriteDB.getInit(ProviderContext.getContext())
                          .getFavorite()
                          .insert(FavoriteList(0,listSave))

            }else  FavoriteDB.getInit(ProviderContext.getContext())
                             .getFavorite()
                             .update(FavoriteList(0,listSave))
        }
    }
}