package com.adnroidapp.amazingappnasa.ui.fragment

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.adnroidapp.amazingappnasa.App
import com.adnroidapp.amazingappnasa.R

class SettingPrefFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting_pref)
        activity?.let {
            findPreference<ListPreference>(it.getString(R.string.KEY_THEME))?.apply {
                setOnPreferenceChangeListener { _, newValue ->
                    val oldTheme = App.instance.getNameThemeSetting()
                    if (newValue != oldTheme) {
                        activity?.recreate()
                    }
                    true
                }
            }
        }
    }
}