package com.adnroidapp.amazingappnasa.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.adnroidapp.amazingappnasa.App
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.database.dbData.NotesData
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesFragment : Fragment(R.layout.fragment_notes) {

    companion object {
        fun newInstance() = NotesFragment()
        const val TAG = "NotesFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        btn_save_notes.setOnClickListener {

            Log.v("Thread", Thread.currentThread().name)

            activity?.let {
                CoroutineScope(Dispatchers.IO).launch {
                    Log.v("Thread", Thread.currentThread().name)
                    App.db.notes().addNotes(getNotes())

                    withContext(Dispatchers.Main) {
                        Log.v("Thread", Thread.currentThread().name)
                        it.supportFragmentManager.popBackStack()
                    }
                }
            }
        }
    }

    private fun getNotes() : NotesData {
        val nameNotes = text_name_notes.text.toString()
        val messageNotes = btn_save_notes.text.toString()
        return NotesData(nameNotes = nameNotes, message = messageNotes)
    }

    private fun initToolbar() {
        toolbar_notes.setNavigationIcon(R.drawable.ic_baseline_arrow_back_32)
        toolbar_notes.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}