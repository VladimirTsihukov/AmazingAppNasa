package com.adnroidapp.amazingappnasa.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.adnroidapp.amazingappnasa.App
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.ThemesEnum
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFragment : Fragment(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setChipClick()

        view.btn_save_theme.setOnClickListener {
            when (view.chip_group.checkedChipId) {
                chip_mars.id -> setThemes(ThemesEnum.MARS)
                chip_nasa.id -> setThemes(ThemesEnum.NASA)
                chip_moon.id -> setThemes(ThemesEnum.MOON)
                else -> Unit
            }
        }
    }

    private fun setChipClick() {
        when (App.instance.getNameTheme()) {
            ThemesEnum.NASA.name -> chip_nasa.performClick()
            ThemesEnum.MARS.name -> chip_mars.performClick()
            ThemesEnum.MOON.name -> chip_moon.performClick()
            else -> Unit
        }
    }

    private fun setThemes(nameThemes: ThemesEnum) {
        App.instance.setNameThemeSharedPref(nameThemes)
        activity?.recreate()
    }

    companion object {
        fun newInstance() = SettingFragment()
        const val TAG = "SettingFragment"
    }
}