package com.appli.picot.meteoapp.city

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Toast
import com.appli.picot.meteoapp.App
import com.appli.picot.meteoapp.DataBase
import com.appli.picot.meteoapp.R


class CityFragment: Fragment() {

    lateinit var cities: MutableList<City>
    private lateinit var database: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cities = mutableListOf()
        database = App.database
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.city_fragment, container, false)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.fragment_city, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.createCityItem -> {
                val dialog = CreateCityDialog()
                dialog.listener = object: CreateCityDialog.CreateCityDialogListener {
                    override fun positiveClick(cityName: String) {
                        saveCity(City(cityName))
                    }
                    override fun negativeClick() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
                dialog.show(fragmentManager, "CityFragment")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveCity(city: City){
        if (App.database.addCity(city)) {
            cities.add(city)
        } else {
            Toast.makeText(context, getString(R.string.errorToastSaveCityFragment), Toast.LENGTH_LONG).show()
        }
    }
}