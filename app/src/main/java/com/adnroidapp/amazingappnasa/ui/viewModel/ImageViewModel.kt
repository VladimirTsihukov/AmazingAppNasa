package com.adnroidapp.amazingappnasa.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.TAG_MAIN
import com.adnroidapp.amazingappnasa.api.ApiFactory
import com.adnroidapp.amazingappnasa.data.ImageDataClass
import com.adnroidapp.amazingappnasa.data.PhotoRoverInMars
import com.adnroidapp.amazingappnasa.data.ResponseImageEarth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageViewModel(application: Application) : AndroidViewModel(application) {

    val liveDataImage = MutableLiveData<List<ImageDataClass>>()
    private val contextApplication: Application by lazy { application }

    init {
        getImagesEarthInServer()
    }

    fun getImagesEarthInServer() {
        ApiFactory.getApiService(ApiFactory.URL_IMAGES_EARTH).getListImageEarth("2021-03-25")
            .enqueue(object : Callback<List<ResponseImageEarth>> {
                override fun onResponse(
                    call: Call<List<ResponseImageEarth>>,
                    response: Response<List<ResponseImageEarth>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val imageDataClass = mutableListOf<ImageDataClass>()
                            if (it.isNotEmpty()) {
                                it.forEachIndexed { _, responseItem ->
                                    imageDataClass.add(ImageDataClass(
                                        image = responseItem.image,
                                        date = responseItem.date,
                                        namePlanet = contextApplication.resources.getString(R.string.planet_earth)))
                                }
                            }
                            liveDataImage.postValue(imageDataClass)
                        }
                    }
                }

                override fun onFailure(call: Call<List<ResponseImageEarth>>, t: Throwable) {
                    Log.v(TAG_MAIN, t.message.toString())
                }

            })
    }

    fun getPhotoMarsInServer() {
        ApiFactory.getApiService(ApiFactory.URL_IMAGES_ROVER_MARS).getPhotoRoverInMars()
            .enqueue(object : Callback<PhotoRoverInMars> {
                override fun onResponse(
                    call: Call<PhotoRoverInMars>,
                    response: Response<PhotoRoverInMars>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val imageDataClass = mutableListOf<ImageDataClass>()
                            if (it.photos.isNotEmpty()) {
                                it.photos.forEachIndexed { _, photosItem ->
                                    imageDataClass.add(ImageDataClass(
                                        image = photosItem.imgSrc,
                                        date = photosItem.earthDate,
                                        namePlanet = contextApplication.resources.getString(R.string.planet_mars)))
                                }
                                liveDataImage.postValue(imageDataClass)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<PhotoRoverInMars>, t: Throwable) {
                    Log.v(TAG_MAIN, t.message.toString())
                }
            })
    }
}