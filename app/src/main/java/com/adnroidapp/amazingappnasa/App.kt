package com.adnroidapp.amazingappnasa

import android.app.Application
import androidx.preference.PreferenceManager

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun getNameThemeSetting(): String {
        val prefSetting = PreferenceManager.getDefaultSharedPreferences(this)
        return prefSetting.getString(this.getString(R.string.KEY_THEME), ThemesEnum.NASA.name) ?: ThemesEnum.NASA.name
    }
}