package com.adnroidapp.amazingappnasa.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.data.NotesData
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment : Fragment(R.layout.fragment_notes) {

    private lateinit var mListener: OnFragmentGetDataNotes

    companion object {
        fun newInstance() = NotesFragment()
        const val TAG = "NotesFragment"
        const val KEY_NOTES_BUNDLE = "KEY_NOTES_BUNDLE"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()

        btn_save_notes.setOnClickListener {

            activity?.let {
                mListener.getFragmentNotes(getNotes())
                it.supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mListener = activity as OnFragmentGetDataNotes
    }

    private fun getNotes() : NotesData {
        val nameNotes = text_name_notes.text.toString()
        val messageNotes = btn_save_notes.text.toString()
        return NotesData(id = 0, nameNotes = nameNotes, message = messageNotes)
    }

    private fun initToolbar() {
        toolbar_notes.setNavigationIcon(R.drawable.ic_baseline_arrow_back_32)
        toolbar_notes.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}