package com.adnroidapp.amazingappnasa.ui.adapter.itemTouchHelper

import androidx.recyclerview.widget.RecyclerView

interface OnStartDragListener {

    //сообщает что пользователь намерен пересестить элемент
    fun onStartDragListener(viewHolder: RecyclerView.ViewHolder)
}