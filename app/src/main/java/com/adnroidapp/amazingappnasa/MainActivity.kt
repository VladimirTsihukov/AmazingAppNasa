package com.adnroidapp.amazingappnasa

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.adnroidapp.amazingappnasa.ui.picture.PicturesOfTheDayFragment

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        Log.v(TAG, BuildConfig.NASA_API_KEY)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PicturesOfTheDayFragment.newInstance())
                    .commitNow()
        }
    }
}