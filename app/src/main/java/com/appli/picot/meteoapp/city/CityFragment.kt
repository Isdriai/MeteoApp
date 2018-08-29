package com.appli.picot.meteoapp.city

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.Toast
import com.appli.picot.meteoapp.App
import com.appli.picot.meteoapp.DataBase
import com.appli.picot.meteoapp.R


class CityFragment: Fragment(), CityAdapter.CityItemListener {


    lateinit var cities: MutableList<City>
    private lateinit var database: DataBase
    private lateinit var recylerView: RecyclerView
    private lateinit var adapter: CityAdapter

    val TAG = CityFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cities = mutableListOf()
        database = App.database
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.city_fragment, container, false)
        recylerView = view.findViewById(R.id.listCity)
        recylerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cities = database.getAllCities()
        adapter = CityAdapter(cities, this)
        recylerView.adapter = adapter
    }

    override fun citySelected(city: City) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cityDeleted(city: City) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
            adapter.notifyDataSetChanged()
            Log.i(TAG, "ville ajoutée")
        } else {
            Toast.makeText(context, getString(R.string.errorToastSaveCityFragment), Toast.LENGTH_LONG).show()
        }
    }
}