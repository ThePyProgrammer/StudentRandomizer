package com.thepyprogrammer.randstudent.ui.add

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.thepyprogrammer.randstudent.R
import java.time.LocalDate

open class AddListDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Create List")
        // set the custom layout
        val customLayout: View = LayoutInflater.from(activity?.applicationContext).inflate(
            R.layout.expanded_item_todo,
            null
        )
        builder.setView(customLayout)
        // create and show the alert dialog



        builder.setPositiveButton(
            "OK"
        ) { _, _ ->

        }

        val dialog: AlertDialog = builder.create()
        return dialog
    }
}