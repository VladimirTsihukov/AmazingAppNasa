package com.adnroidapp.amazingappnasa.api

import com.adnroidapp.amazingappnasa.data.NasaImageResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiServiсe {

    @GET("planetary/apod")
    fun getNasaImage() : Call<NasaImageResponse>
}