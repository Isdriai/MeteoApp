package com.appli.picot.meteoapp.city

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.InputType
import android.widget.EditText
import com.appli.picot.meteoapp.R

class CreateCityDialog: DialogFragment() {

    interface CreateCityDialogListener {
        fun positiveClick(cityName: String)
        fun negativeClick()
    }

    var listener: CreateCityDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        val input = EditText(context)
        with(input) {
            inputType = InputType.TYPE_CLASS_TEXT
            hint = context.getString(R.string.createcity_cityhint)
        }

        builder.setTitle(getString(R.string.createcity_title))
                .setView(input)
                .setPositiveButton(getString(R.string.createcity_positive), DialogInterface.OnClickListener { _, _ ->
                    listener?.positiveClick(input.text.toString())
                })
                .setNegativeButton(getString(R.string.createcity_negative), DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                    listener?.negativeClick()
                })

        return builder.create()
    }
}