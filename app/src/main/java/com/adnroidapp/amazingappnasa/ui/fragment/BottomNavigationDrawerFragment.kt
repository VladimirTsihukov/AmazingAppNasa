package com.adnroidapp.amazingappnasa.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.adnroidapp.amazingappnasa.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_navigation_layout.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_navigation_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_one -> {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.container, NotesListFragment.newInstance(), NotesListFragment.TAG)
                        ?.addToBackStack(NotesFragment.TAG)?.commit()
                    dismiss()
                }
                R.id.navigation_two -> {Toast.makeText(context, "Screan two", Toast.LENGTH_SHORT)
                    .show()
                    dismiss()
                }
            }
            true
        }
    }
    companion object {
        const val TAG = "BottomNavigationDrawerFragment"
    }
}