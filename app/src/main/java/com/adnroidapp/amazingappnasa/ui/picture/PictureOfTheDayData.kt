package com.adnroidapp.amazingappnasa.ui.picture

import com.adnroidapp.amazingappnasa.data.NasaImageResponse

sealed class PictureOfTheDayData {
    data class Success(val serverResponse: NasaImageResponse) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    data class Loading(val progress: Int?) : PictureOfTheDayData()
}