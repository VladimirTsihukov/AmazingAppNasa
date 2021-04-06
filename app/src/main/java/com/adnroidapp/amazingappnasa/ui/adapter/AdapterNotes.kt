package com.adnroidapp.amazingappnasa.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.amazingappnasa.R
import com.adnroidapp.amazingappnasa.data.NotesData
import kotlinx.android.synthetic.main.view_holder_notes.view.*

class AdapterNotes : RecyclerView.Adapter<AdapterNotes.HolderNotes>() {

    private var listNotes = mutableListOf<Pair<NotesData, Boolean>>()

    fun setItem(newListNotes: List<Pair<NotesData, Boolean>>) {
        listNotes.addAll(newListNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderNotes {
        return HolderNotes(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_notes, parent, false))
    }

    override fun onBindViewHolder(holder: HolderNotes, position: Int) {
        holder.onBind(listNotes[position].first)
    }

    override fun getItemCount(): Int = listNotes.size

    inner class HolderNotes(view: View) : RecyclerView.ViewHolder(view) {
        private var isCheckedMessage = false

        fun onBind(notes: NotesData) {
            with(itemView) {
                tv_name_notes.text = notes.nameNotes
                tv_message_notes.text = notes.message
                img_delete_notes.setOnClickListener { deleteNotes() }
                img_item_down.setOnClickListener { setCheckMessage() }
                img_item_up.setOnClickListener { setCheckMessage() }
            }
        }

        private fun deleteNotes() {
            listNotes.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
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
    }
}