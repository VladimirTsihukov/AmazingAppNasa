package com.adnroidapp.amazingappnasa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adnroidapp.amazingappnasa.ui.picture.PicturesOfTheDayFragment

const val TAG_MAIN = "MainActivity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        when (App.instance.getNameTheme()) {
            ThemesEnum.NASA.name -> setTheme(R.style.Theme_Nasa)
            ThemesEnum.MARS.name -> setTheme(R.style.Theme_Mars)
            ThemesEnum.MOON.name -> setTheme(R.style.Theme_Moon)
            else -> Unit
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PicturesOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}