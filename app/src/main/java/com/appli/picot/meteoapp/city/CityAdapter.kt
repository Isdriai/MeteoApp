package com.appli.picot.meteoapp.city

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appli.picot.meteoapp.R

data class CityAdapter(val cities: MutableList<City>, val itemClickListener: CityAdapter.CityItemListener): RecyclerView.Adapter<CityAdapter.ViewHolder>(), View.OnClickListener{


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cardView = itemView.findViewById<CardView>(R.id.cityCardview)
        val cityTextview = itemView.findViewById<TextView>(R.id.cityTextview)
    }

    interface CityItemListener {
        fun citySelected(city: City)
        fun cityDeleted(city: City)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewItem = inflater.inflate(R.layout.city_item, parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
       return cities.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cities[position]
        holder.cityTextview.text = city.name
        holder.cardView.tag = city
        holder.cardView.setOnClickListener(this@CityAdapter)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.cityCardview -> itemClickListener.citySelected(view.tag as City)
        }
    }

}