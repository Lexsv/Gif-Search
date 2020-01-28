package ua.com.gifsearch.presenters.search

import android.util.Log
import androidx.lifecycle.Observer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.com.gifsearch.R
import ua.com.gifsearch.reposetory.localstore.Store
import ua.com.gifsearch.reposetory.retrofit.GifObject
import ua.com.gifsearch.reposetory.retrofit.ServiceApi
import ua.com.gifsearch.reposetory.retrofit.TenorGif
import ua.com.gifsearch.utils.ProviderContext
import ua.com.gifsearch.utils.isNet
import ua.com.gifsearch.utils.resourceToSring
import javax.inject.Inject

class SearchPresenter @Inject constructor(var view: ISearch.View):  ISearch.Presenter{

    val KEY = resourceToSring(R.string.TENOR_KEY)
    override fun onStart() {
        Store.getListGif().observe(view.getLifecycleOwner(), Observer {
           view.showRecyclerList(it.results)
        })
    }

    override fun loadListGif(search: String, queue: Int) {
        if (!isNet(ProviderContext.getContext())) {
            view.showMessage("Подключите Интернет")
        }else{
            ServiceApi.getJSONApi()
                      .getListRelevantGif(search,KEY ,queue)
                      .enqueue(object : Callback<TenorGif> {
                          override fun onFailure(call: Call<TenorGif>, t: Throwable) {
                              if (t.message == null){
                                  Log.i("Failure loadListGif","Error of message is null")
                              }else Log.i("Failure loadListGif",t.message!!)
                          }

                          override fun onResponse(call: Call<TenorGif>, response: Response<TenorGif>) {
                              Store.getListGif().value = response.body()
                              view.hideProgress()
                              view.hideKeyBoard()
                          }

                      })
        }


    }

    override fun listNext(search: String, queue: Int) {
        if (!isNet(ProviderContext.getContext())) {
            view.showMessage("Подключите Интернет")

        }else {
            ServiceApi.getJSONApi().getNextRelevantGif(search,KEY,queue)
                .enqueue(object : Callback<TenorGif>{
                    override fun onFailure(call: Call<TenorGif>, t: Throwable) {
                        if (t.message == null){
                            Log.i("Failure loadListGif","Error of message is null")
                        }else Log.i("Failure loadListGif",t.message!!)
                    }

                    override fun onResponse(call: Call<TenorGif>, response: Response<TenorGif>) {
                        if (response.body()!!.next == 0){
                            view.finishLoad()
                        }else{
                            view.showNextListGif(response.body()!!.results)
                        }
                    }

                })
        }
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