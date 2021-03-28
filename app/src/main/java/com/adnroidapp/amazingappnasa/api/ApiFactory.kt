package com.adnroidapp.amazingappnasa.api


import com.adnroidapp.amazingappnasa.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    const val URL_PICTURE_OF_THE_DAY = "https://api.nasa.gov/"
    const val URL_IMAGES_EARTH = "https://api.nasa.gov/EPIC/api/natural/date/"
    const val URL_IMAGES_ROVER_MARS = "https://api.nasa.gov/mars-photos/api/"

    private const val API_KEY = "api_key"

    private val interceptor = Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.NASA_API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(interceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))   //write in log request with server
        .build()

    private fun gson() = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .setLenient()
        .create()

    private fun retrofitImageNasa(url : String): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson()))
        .build()

    fun getApiService(url : String): ApiServiсe = retrofitImageNasa(url).create(ApiServiсe::class.java)
}