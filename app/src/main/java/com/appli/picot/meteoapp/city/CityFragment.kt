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

    interface CityFragmentListener {
        fun onCitySelected(city: City)
        fun onEmptyCities()
    }

    var listener: CityFragmentListener? = null

    lateinit var cities: MutableList<City>
    private lateinit var database: DataBase
    private lateinit var recyclerView: RecyclerView
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
        recyclerView = view.findViewById(R.id.listCity)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cities = database.getAllCities()
        adapter = CityAdapter(cities, this)
        recyclerView.adapter = adapter
    }

    override fun citySelected(city: City) {
        listener?.onCitySelected(city)
    }

    override fun cityDeleted(city: City) {
        val deleteCityFragment = DeleteCityDialog.newInstance(city.name)
        deleteCityFragment.listener = object: DeleteCityDialog.DeleteCityDialogListener {
            override fun negativeClick() {

            }

            override fun positiveClick() {
                if(database.deleteCity(city)){
                    Log.i(TAG, "suppression cote fragment")
                    cities.remove(city)
                    adapter.notifyDataSetChanged()
                    selectFirsCity()
                    Toast.makeText(deleteCityFragment.context, getString(R.string.city_removed_toast), Toast.LENGTH_LONG).show()
                }
            }
        }

        deleteCityFragment.show(fragmentManager, "DeleteCityDialogFragment")
    }

    fun selectFirsCity(){
        when(cities.isEmpty()){
            true -> listener?.onEmptyCities()
            false -> citySelected(cities.first())
        }
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