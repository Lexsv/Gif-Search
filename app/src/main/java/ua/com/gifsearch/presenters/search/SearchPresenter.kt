package ua.com.gifsearch.presenters.search

import androidx.lifecycle.Observer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.com.gifsearch.R
import ua.com.gifsearch.reposetory.localstore.Store
import ua.com.gifsearch.reposetory.retrofit.GifObject
import ua.com.gifsearch.reposetory.retrofit.ServiseApi
import ua.com.gifsearch.reposetory.retrofit.TenorGif
import ua.com.gifsearch.utils.ProvidContext
import ua.com.gifsearch.utils.isNet
import javax.inject.Inject

class SearchPresenter @Inject constructor(var view: ISearch.View):  ISearch.Presenter{


    override fun onStart() {
        Store.getListGif().observe(view.getLifecycleOwner(), Observer {
           view.showRecyclerList(it.results)
        })
    }

    override fun loadListGif(search: String, queue: Int) {
        if (isNet(ProvidContext.getContext())) {
            ServiseApi.getJSONApi().getListRelevantGif(search,ProvidContext.getContext().resources.getString( R.string.TENOR_KEY),queue)
                .enqueue(object : Callback<TenorGif> {
                    override fun onFailure(call: Call<TenorGif>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<TenorGif>, response: Response<TenorGif>) {
                        Store.getListGif().value = response.body()
                        view.hideKeyBoard()
                    }

                })
        }else view.swohMessag("Подключите Интернет")

    }

    override fun listNext(search: String, queue: Int) {
        if (isNet(ProvidContext.getContext())) {
            ServiseApi.getJSONApi().getNextRelevantGif(search,ProvidContext.getContext().resources.getString( R.string.TENOR_KEY),queue)
                .enqueue(object : Callback<TenorGif>{
                    override fun onFailure(call: Call<TenorGif>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<TenorGif>, response: Response<TenorGif>) {
                        if (response.body()!!.next == 0){
                            view.finishLoad()
                        }else{
                            view.swohNextListGif(response.body()!!.results)
                        }
                    }

                })
        }else view.swohMessag("Подключите Интернет")

    }

    override fun addInFavorite(item: GifObject) {
        val tempList = Store.getListFavorite().value
        if(item.favorite){

            if (tempList == null){
                Store.getListFavorite().value = arrayListOf(item)
            }else {
                tempList.add(item)
                Store.getListFavorite().value = tempList

            }

        }else{
            tempList!!.remove(item)
            Store.getListFavorite().value = tempList

        }
    }

    override fun onClickCardView(item: GifObject) {
        view.showGif(item)
    }
}