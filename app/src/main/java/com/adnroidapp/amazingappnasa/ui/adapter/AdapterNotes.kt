package com.adnroidapp.amazingappnasa.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.amazingappnasa.App
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.database.dbData.NotesData
import com.adnroidapp.amazingappnasa.ui.adapter.itemTouchHelper.ItemTouchHelperAdapter
import com.adnroidapp.amazingappnasa.ui.adapter.itemTouchHelper.ItemTouchHelperViewHolder
import com.adnroidapp.amazingappnasa.ui.adapter.itemTouchHelper.OnStartDragListener
import kotlinx.android.synthetic.main.view_holder_notes.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdapterNotes(private val onStartDragListener: OnStartDragListener) :
    RecyclerView.Adapter<AdapterNotes.HolderNotes>(), ItemTouchHelperAdapter {

    private var listNotes = mutableListOf<NotesData>()

    fun setItem(newListNotes: List<NotesData>) {
        listNotes.clear()
        listNotes.addAll(newListNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderNotes {
        return HolderNotes(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_notes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HolderNotes, position: Int) {
        holder.onBind(listNotes[position])
    }

    override fun getItemCount(): Int = listNotes.size

    fun addNotes(notes: NotesData) {
        listNotes.add(notes)
        notifyItemInserted(listNotes.size)
    }

    inner class HolderNotes(private val view: View) : RecyclerView.ViewHolder(view),
        ItemTouchHelperViewHolder {
        private var isCheckedMessage = false

        @SuppressLint("ClickableViewAccessibility")
        fun onBind(notes: NotesData) {
            with(itemView) {
                tv_name_notes.text = notes.nameNotes
                tv_message_notes.text = notes.message
                img_delete_notes.setOnClickListener { deleteNotes() }
                img_item_down.setOnClickListener { setCheckMessage() }
                img_item_up.setOnClickListener { setCheckMessage() }
                img_drag_drop.setOnTouchListener { _, motionEvent ->
                    if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                        onStartDragListener.onStartDragListener(this@HolderNotes)
                    }
                    false
                }
            }
        }

        private fun deleteNotes() {
            CoroutineScope(Dispatchers.IO).launch {
                App.db.notes().deleteNotes(listNotes[layoutPosition])

                withContext(Dispatchers.Main) {
                    listNotes.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
            }
        }

        private fun setCheckMessage() {
            with(itemView) {
                if (isCheckedMessage) {
                    img_item_down.visibility = View.VISIBLE
                    img_item_up.visibility = View.INVISIBLE
                    tv_message_notes.visibility = View.GONE
                } else {
                    img_item_down.visibility = View.INVISIBLE
                    img_item_up.visibility = View.VISIBLE
                    tv_message_notes.visibility = View.VISIBLE
                }
            }
            isCheckedMessage = !isCheckedMessage
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(view.context.getColor(R.color.purple_200))
        }

        override fun onItemClea() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    //перетаскиваем item
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        listNotes.removeAt(fromPosition).apply {
            listNotes.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    //удаляем элемент по свайпу
    override fun itemDismiss(position: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            App.db.notes().deleteNotes(listNotes[position])

            withContext(Dispatchers.Main) {
                listNotes.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }
}