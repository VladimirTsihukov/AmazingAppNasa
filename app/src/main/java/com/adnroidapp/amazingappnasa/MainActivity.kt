package com.adnroidapp.amazingappnasa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adnroidapp.amazingappnasa.ui.fragment.PicturesOfTheDayFragment

const val TAG_MAIN = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (App.instance.getNameThemeSetting()) {
            ThemesEnum.NASA.name -> setTheme(R.style.Nasa)
            ThemesEnum.MARS.name -> setTheme(R.style.Mars)
            ThemesEnum.MOON.name -> setTheme(R.style.Moon)
            else -> Unit
        }

        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PicturesOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}