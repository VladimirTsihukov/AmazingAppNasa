package com.adnroidapp.amazingappnasa.api

import com.adnroidapp.amazingappnasa.data.NasaImageResponse
import com.adnroidapp.amazingappnasa.data.PhotoRoverInMars
import com.adnroidapp.amazingappnasa.data.ResponseImageEarth
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServiсe {

    @GET("planetary/apod")
    fun getNasaImage(@Query("date") date: String): Call<NasaImageResponse>

    @GET("{date}")
    fun getListImageEarth(@Path("date") date: String): Call<List<ResponseImageEarth>>

    @GET("v1/rovers/curiosity/photos?sol=1000&page=1")
    fun getPhotoRoverInMars() : Call<PhotoRoverInMars>
}