package com.adnroidapp.amazingappnasa.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adnroidapp.amazingappnasa.App
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.ThemesEnum
import com.adnroidapp.amazingappnasa.ui.fragment.PicturesOfTheDayFragment
import com.adnroidapp.amazingappnasa.ui.fragment.SplashFragment

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
            
            supportFragmentManager.beginTransaction()
                .add(R.id.container, SplashFragment.newInstance(), SplashFragment.TAG)
                .addToBackStack(SplashFragment.TAG)
                .commit()
        }
    }
}