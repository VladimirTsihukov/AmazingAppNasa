package com.adnroidapp.amazingappnasa.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.adnroidapp.amazingappnasa.R
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .replace(R.id.container_setting, SettingPrefFragment())
                .commit()
        }
    }

    private fun initToolbar() {
        toolbar_setting.setNavigationIcon(R.drawable.ic_baseline_arrow_back_32)
        toolbar_setting.setNavigationOnClickListener {
            activity?.let {
                it.supportFragmentManager.popBackStack()
            }
        }
    }

    companion object {
        fun newInstance() = SettingFragment()
        const val TAG = "SettingFragment"
    }
}