package ua.com.gifsearch.reposetory.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JSONPlaceHolderApi {

    @GET("search?")
    fun getListRelevantGif (
        @Query("q") searchEmpty: String,
        @Query("key") key: String,
        @Query("limit") limit: Int,
        @Query("media_filter") filter: String = "minimal"
        ): Call<TenorGif>

    @GET("search?")
    fun getNextRelevantGif (
        @Query("q") searchEmpty: String,
        @Query("key") key: String,
        @Query("pos") limit: Int,
        @Query("media_filter") filter: String = "minimal"
        ): Call<TenorGif>


    @GET("gifs?")
    fun getGifById(
        @Query("key") key: String,
        @Query("ids") id : String,
        @Query("media_filter") filter: String = "minimal"
    ): Call<TenorGif>

}