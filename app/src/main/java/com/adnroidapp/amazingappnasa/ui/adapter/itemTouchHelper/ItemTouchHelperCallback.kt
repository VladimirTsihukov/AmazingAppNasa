package com.adnroidapp.amazingappnasa.ui.adapter.itemTouchHelper

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.adnroidapp.amazingappnasa.ui.adapter.AdapterNotes
import kotlin.math.abs

class ItemTouchHelperCallback(private val adapter: AdapterNotes) : ItemTouchHelper.Callback() {

    //включает возможность свайпа
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    //включает возможность перетаскивания по длинному нажатию
    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    //определяет направления перетаскивания
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlag = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlag, swipeFlag)
    }

    //оповещает наш адаптер о изменении положения, чтобы адаптер обработал это действие
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onItemMove(viewHolder.layoutPosition, target.adapterPosition)
        return true
    }

    //оповещает наш адаптер о удаления, чтобы адаптер обработал это действие
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
       adapter.itemDismiss(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            (viewHolder as ItemTouchHelperViewHolder).onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        (viewHolder as ItemTouchHelperViewHolder).onItemClea()
    }

    //метод добовляющий затемнения viewHolder при смахивании
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val width = viewHolder.itemView.width.toFloat()
            val alpha = 1.0f - abs(dX) / width
            viewHolder.itemView.alpha = alpha
            viewHolder.itemView.translationX = dX
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}