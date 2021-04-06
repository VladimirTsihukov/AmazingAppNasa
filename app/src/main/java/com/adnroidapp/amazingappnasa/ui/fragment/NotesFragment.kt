package com.adnroidapp.amazingappnasa.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.adnroidapp.amazingappnasa.R
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment : Fragment(R.layout.fragment_notes) {

    companion object {
        fun newInstance() = NotesFragment()
        const val TAG = "NotesFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar_notes.setNavigationIcon(R.drawable.ic_baseline_arrow_back_32)
        toolbar_notes.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}