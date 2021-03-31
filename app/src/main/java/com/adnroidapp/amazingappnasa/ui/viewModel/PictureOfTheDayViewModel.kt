package com.adnroidapp.amazingappnasa.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adnroidapp.amazingappnasa.ClassKey.TODAY
import com.adnroidapp.amazingappnasa.api.ApiFactory
import com.adnroidapp.amazingappnasa.data.NasaImageResponse
import com.adnroidapp.amazingappnasa.getDate

import com.adnroidapp.amazingappnasa.ui.PictureOfTheDayData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(application: Application) : AndroidViewModel(application) {

    val liveDataForViewToObserver: MutableLiveData<PictureOfTheDayData> = MutableLiveData()

    init {
        getPhotoOfTheDatInServer(TODAY)
    }

    fun getPhotoOfTheDatInServer(day: Int) {
        liveDataForViewToObserver.value = PictureOfTheDayData.Loading(null)
        ApiFactory.getApiService(ApiFactory.URL_PICTURE_OF_THE_DAY).getNasaImage(getDate(day))
            .enqueue(object : Callback<NasaImageResponse> {
                override fun onResponse(
                    call: Call<NasaImageResponse>,
                    response: Response<NasaImageResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            liveDataForViewToObserver.postValue(PictureOfTheDayData.Success(it))
                        }
                    }
                }

                override fun onFailure(call: Call<NasaImageResponse>, t: Throwable) {
                    liveDataForViewToObserver.value = PictureOfTheDayData.Error(t)
                }
            })
    }
}