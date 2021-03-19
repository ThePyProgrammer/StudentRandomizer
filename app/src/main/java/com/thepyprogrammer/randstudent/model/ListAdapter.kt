package com.thepyprogrammer.randstudent.model

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.thepyprogrammer.randstudent.R
import com.thepyprogrammer.randstudent.model.list.StudentList
import com.thepyprogrammer.randstudent.ui.AppMainActivity
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter(
    private val activity: AppMainActivity,
    val lists: MutableList<StudentList> = mutableListOf()
): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var recentlyDeleted: StudentList? = null
    var recentlyDeletedPosition: Int = -1

    class ListViewHolder(itemView: CardView) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_list,
            parent,
            false
        ) as CardView)

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val curList = lists[position]
        holder.itemView.apply {
            listTitleView.setText(curList.title)
            selection.isSelected = curList.selected

            selection.setOnCheckedChangeListener { _, isChecked ->
                curList.selected = isChecked
                if(isChecked) {
                    lists.forEachIndexed { index, element ->
                        element.selected = index == position
                    }
                }
                notifyDataSetChanged()
            }

            setOnClickListener {

            }

            listTitleView.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_ENTER && listTitleView.text.toString()
                            .isNotEmpty()
                    ) {
                        curList.title = listTitleView.text.toString()
                        // notifyItemChanged(position)
                        notifyDataSetChanged()
                        return true
                    }
                    return false
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    fun deleteItem(position: Int) {
        lists.removeAt(position)
        notifyDataSetChanged()
        val view: View = activity.findViewById(R.id.home)
        val snackbar: Snackbar = Snackbar.make(
            view, "List Has Been Deleted",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("UNDO") { undoDelete() }
        snackbar.show()
        //activity.updateFile()
    }

    private fun undoDelete() {
        recentlyDeleted?.let {
            lists.add(
                recentlyDeletedPosition,
                it
            )
            notifyItemInserted(recentlyDeletedPosition)
            val view: View = activity.findViewById(R.id.home)
            val snackbar: Snackbar = Snackbar.make(
                view, "List Has Been Restored",
                Snackbar.LENGTH_SHORT
            )
            snackbar.show()
        }
    }



}