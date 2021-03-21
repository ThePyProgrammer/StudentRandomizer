package com.thepyprogrammer.randstudent.ui.add

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import com.thepyprogrammer.randstudent.R

open class AddListDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Create List")
        // set the custom layout
        val customLayout: View = LayoutInflater.from(activity?.applicationContext).inflate(
            R.layout.expanded_item_list,
            null
        )
        builder.setView(customLayout)
        // create and show the alert dialog



        builder.setPositiveButton(
            "OK"
        ) { _, _ ->

        }

        return builder.create()
    }
}