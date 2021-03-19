package com.thepyprogrammer.randstudent.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thepyprogrammer.randstudent.R
import com.thepyprogrammer.randstudent.model.list.StudentList
import com.thepyprogrammer.randstudent.ui.AppMainActivity

class ListAdapter(
    private val activity: AppMainActivity,
    val lists: MutableList<StudentList> = mutableListOf()
): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var recentlyDeleted: StudentList? = null
    var recentlyDeletedPosition: Int = -1

    class ListViewHolder(itemView: CardView) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_todo,
            parent,
            false
        ) as CardView)

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return lists.size
    }

    fun deleteItem(position: Int) {
        lists.removeAt(position)
        notifyDataSetChanged()
        val view: View = activity.findViewById(R.id.home)
        val snackbar: Snackbar = Snackbar.make(
            view, "Task Has Been Deleted",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("UNDO") { undoDelete() }
        snackbar.show()
        //activity.updateFile()
    }

    private fun undoDelete() {
        recentlyDeleted?.let {
            todos.add(
                recentlyDeletedPosition,
                it
            )
            notifyItemInserted(recentlyDeletedPosition)
            val view: View = activity.findViewById(R.id.home)
            val snackbar: Snackbar = Snackbar.make(
                view, "Task Has Been Restored",
                Snackbar.LENGTH_SHORT
            )
            snackbar.show()
        }
    }


}