package ua.com.gifsearch.models

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.com.gifsearch.reposetory.localstore.Store
import ua.com.gifsearch.reposetory.retrofit.JSONPlaceHolderApi
import ua.com.gifsearch.reposetory.retrofit.ServiseApi
import ua.com.gifsearch.reposetory.retrofit.TenorGif

class ProvidData: IProvidData {

    private var api: JSONPlaceHolderApi

    init {
        api = ServiseApi.getJSONApi()
    }


    fun getGif(searchEmpty: String, key: String, limit: Int) {
        api.getListRelevantGif(searchEmpty, key, limit)
            .enqueue(object : Callback<TenorGif> {


                override fun onResponse(
                    call: Call<TenorGif>,
                    response: Response<TenorGif>
                ) {
                    Store.getListGif().value = response.body()
                }

                override fun onFailure(call: Call<TenorGif>, t: Throwable) {

                }
            })
    }


}