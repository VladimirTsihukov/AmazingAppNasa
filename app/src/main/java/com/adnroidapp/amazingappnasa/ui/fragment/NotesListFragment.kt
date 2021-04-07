package com.adnroidapp.amazingappnasa.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.database.dbData.NotesData
import com.adnroidapp.amazingappnasa.ui.adapter.AdapterNotes
import com.adnroidapp.amazingappnasa.ui.adapter.itemTouchHelper.ItemTouchHelperCallback
import com.adnroidapp.amazingappnasa.ui.adapter.itemTouchHelper.OnStartDragListener
import kotlinx.android.synthetic.main.fragment_list_notes.*

class NotesListFragment : Fragment(R.layout.fragment_list_notes) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemTouchHelper: ItemTouchHelper
    private val adapter by lazy { AdapterNotes(dragListener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        recyclerView = view.findViewById(R.id.rec_view_notes_list)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter

        //подключаем к ItemTouchHelper
        itemTouchHelper=  ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val listNotes = listOf(
            NotesData(0, "Name 0", "Message 0"),
            NotesData(1, "Name 1", "Message 1"),
            NotesData(2, "Name 2", "Message 2"),
            NotesData(3, "Name 3", "Message 3"),
            NotesData(4, "Name 4", "Message 4"),
            NotesData(5, "Name 5", "Message 5"),
        )

        adapter.setItem(listNotes)

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