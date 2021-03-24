package com.adnroidapp.amazingappnasa

import android.app.Application
import android.content.SharedPreferences

class App : Application() {
    private val NAME_SHARED_PREF = "SharedPrefThemes"
    private val NAME_THEME = "NAME_THEME"

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (getNameTheme().isEmpty()) setNameThemeSharedPref(ThemesEnum.NASA)
    }

    fun getNameTheme(): String {
        val sharedPref: SharedPreferences =
            applicationContext.getSharedPreferences(NAME_SHARED_PREF, MODE_PRIVATE)
        return sharedPref.getString(NAME_THEME, "").toString()
    }

    fun setNameThemeSharedPref(nameTheme: ThemesEnum) {
        val sharedPreferences =
            applicationContext.getSharedPreferences(NAME_SHARED_PREF, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(NAME_THEME, nameTheme.name)
        editor.apply()
    }
}