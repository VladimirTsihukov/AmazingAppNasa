package com.adnroidapp.amazingappnasa.ui.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adnroidapp.amazingappnasa.api.ApiFactory
import com.adnroidapp.amazingappnasa.data.NasaImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserver: MutableLiveData<PictureOfTheDayData> = MutableLiveData()
) : ViewModel() {

    fun getData(): LiveData<PictureOfTheDayData> {
        sendServerRequest()
        return liveDataForViewToObserver
    }

    private fun sendServerRequest() {
        liveDataForViewToObserver.value = PictureOfTheDayData.Loading(null)
        ApiFactory.API_SERVICE_NASA_IMAGE.getNasaImage()
            .enqueue(object : Callback<NasaImageResponse> {
                override fun onResponse(
                    call: Call<NasaImageResponse>,
                    response: Response<NasaImageResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            liveDataForViewToObserver.value = PictureOfTheDayData.Success(it)
                        }
                    }
                }

                override fun onFailure(call: Call<NasaImageResponse>, t: Throwable) {
                    liveDataForViewToObserver.value = PictureOfTheDayData.Error(t)
                }

            })
    }
}