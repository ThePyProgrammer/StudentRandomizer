package com.thepyprogrammer.randstudent.model

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(
    val adapter: RecyclerView.Adapter,
    dragDirs: Int = 0,
    swipeDirs: Int = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT): ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        adapter.deleteItem(position)
    }
}