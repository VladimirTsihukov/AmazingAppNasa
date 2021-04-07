package com.adnroidapp.amazingappnasa

import android.app.Application
import androidx.preference.PreferenceManager
import com.adnroidapp.amazingappnasa.database.databaseNotes.DbNotes

class App : Application() {
    companion object {
        lateinit var instance: App
        lateinit var db : DbNotes
    }

    override fun onCreate() {
        super.onCreate()
        db = DbNotes.instance(applicationContext)
        instance = this
    }

    fun getNameThemeSetting(): String {
        val prefSetting = PreferenceManager.getDefaultSharedPreferences(this)
        return prefSetting.getString(this.getString(R.string.KEY_THEME), ThemesEnum.NASA.name) ?: ThemesEnum.NASA.name
    }
}