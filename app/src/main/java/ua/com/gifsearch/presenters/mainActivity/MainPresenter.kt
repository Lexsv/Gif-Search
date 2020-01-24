package ua.com.gifsearch.presenters.mainActivity

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ua.com.gifsearch.reposetory.localstore.Store
import ua.com.gifsearch.reposetory.retrofit.GifObject
import ua.com.gifsearch.reposetory.room.FavoriDB
import ua.com.gifsearch.reposetory.room.FavoritList
import ua.com.gifsearch.utils.ProvidContext
import ua.com.gifsearch.utils.genericType
import javax.inject.Inject

class MainPresenter @Inject constructor(var view: IMaimActivity.View): IMaimActivity.Presenter {


    override fun onStart() {
        val resalt = GlobalScope.async {
            FavoriDB.getInit(ProvidContext.getContext()).getFavorit().getAll()
        }
        GlobalScope.launch {
            val list = resalt.await()

            if (!list.isEmpty()){
                val turnsType = genericType<ArrayList<GifObject>>()
                val ob = Gson().fromJson<ArrayList<GifObject>>(list[0].json,turnsType)
                Store.getListFavorite().postValue(ob)
            }
        }
    }

    override fun onSave() {
        val resalt = GlobalScope.async {
            FavoriDB.getInit(ProvidContext.getContext()).getFavorit().getAll()
        }

        GlobalScope.launch {
            val listSave = Gson().toJson(Store.getListFavorite().value)

            if (resalt.await().isEmpty()){
                FavoriDB.getInit(ProvidContext.getContext()).getFavorit().insert(FavoritList(0,listSave))
            }else  FavoriDB.getInit(ProvidContext.getContext()).getFavorit().update(FavoritList(0,listSave))
        }
    }
}