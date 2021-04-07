package com.adnroidapp.amazingappnasa.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.database.dbData.NotesData
import com.adnroidapp.amazingappnasa.ui.adapter.AdapterNotes
import com.adnroidapp.amazingappnasa.ui.adapter.itemTouchHelper.ItemTouchHelperCallback
import com.adnroidapp.amazingappnasa.ui.adapter.itemTouchHelper.OnStartDragListener
import com.adnroidapp.amazingappnasa.ui.viewModel.ListNotesViewModel
import kotlinx.android.synthetic.main.fragment_list_notes.*

class NotesListFragment : Fragment(R.layout.fragment_list_notes) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemTouchHelper: ItemTouchHelper
    private val adapter by lazy { AdapterNotes(dragListener) }
    private val viewModel: ListNotesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        recyclerView = view.findViewById(R.id.rec_view_notes_list)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter

        //подключаем к ItemTouchHelper
        itemTouchHelper=  ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        viewModel.liveDataListNotes.observe(viewLifecycleOwner, { listNotes ->
            listNotes?.let {
                adapter.setItem(listNotes)
            }
        })

        action_bar_add_notes.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .add(R.id.container, NotesFragment.newInstance(), NotesFragment.TAG)
                    .addToBackStack(NotesFragment.TAG).commit()
            }
        }
    }

    fun addNotes(notesData: NotesData) {
        adapter.addNotes(notesData)
    }

    private fun initToolbar () {
        toolbar_list_notes.setNavigationIcon(R.drawable.ic_baseline_arrow_back_32)
        toolbar_list_notes.setNavigationOnClickListener {
            activity?.let {
                it.supportFragmentManager.popBackStack()
            }
        }
    }

    private val dragListener = object : OnStartDragListener {
        override fun onStartDragListener(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper.startDrag(viewHolder)
        }
    }

    companion object {
        fun newInstance() = NotesListFragment()
        const val TAG = "NotesListFragment"
    }
}