package ua.com.gifsearch.reposetory.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiseApi {
    companion object {
        private val BASE_URL = "https://api.tenor.com/v1/"
        private var retrofit: Retrofit? = null

        fun getJSONApi(): JSONPlaceHolderApi {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(JSONPlaceHolderApi::class.java)
        }
    }
}