package com.appli.picot.meteoapp.city

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.appli.picot.meteoapp.R

class DeleteCityDialog: DialogFragment() {

    interface DeleteCityDialogListener {
        fun positiveClick()
        fun negativeClick()
    }

    companion object {

        val EXTRA_CITY_NAME ="training.kotlin.weather.extras.EXTRA_CITY_NAME"

        fun newInstance(cityName: String): DeleteCityDialog {
            val fragment = DeleteCityDialog()
            fragment.arguments = Bundle().apply{
                putString(EXTRA_CITY_NAME, cityName)
            }
            return fragment
        }
    }

    var listener: DeleteCityDialogListener? = null

    private lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityName = arguments!!.getString(EXTRA_CITY_NAME)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.deleteCity_Title, cityName))
                .setPositiveButton(getString(R.string.deleteCity_positive), DialogInterface.OnClickListener{ _, _ -> listener?.positiveClick()})
                .setNegativeButton(getString(R.string.deleteCity_negative), DialogInterface.OnClickListener({ _, _ -> listener?.negativeClick()}))

        return builder.create()
    }
}