package com.adnroidapp.amazingappnasa.ui.adapter.itemTouchHelper

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int) // будет вызван когда элементы перетянутся
    //на достаточное расстояние, чтобы запустить анимацию перемещения

    fun itemDismiss(position: Int) //будет вызываться во время свайпа по элементу

}