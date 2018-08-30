package com.appli.picot.meteoapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.appli.picot.meteoapp.city.City

private const val DATABASE_NAME ="appmeteo.db"
private const val DATABASE_VERSION = 1

private const val CITY_TABLE_NAME = "city"
private const val CITY_KEY_ID = "id"
private const val CITY_KEY_NAME = "name"

private const val CITY_TABLE_CREATE = """
CREATE TABLE $CITY_TABLE_NAME (
    $CITY_KEY_ID INTEGER PRIMARY KEY,
    $CITY_KEY_NAME TEXT)
"""

private const val QUERY_SELECT_ALL = """
    SELECT * FROM $CITY_TABLE_NAME
"""

data class DataBase(val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    val TAG = DataBase::class.java.simpleName

    override fun onCreate(db: SQLiteDatabase?) {
        Log.i("DatabaseOncreate", "onCreate1")
        db?.execSQL(CITY_TABLE_CREATE)
        Log.i("DatabaseOncreate", "onCreate2")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun addCity(city: City) : Boolean{
        val values = ContentValues()
        values.put(CITY_KEY_NAME, city.name)
        Log.i(TAG, "ajout city")
        Log.i(TAG, values.toString())
        city.id = writableDatabase.insert(CITY_TABLE_NAME, null, values)

        return city.id > 0
    }

    fun getAllCities(): MutableList<City>{
        val cities = mutableListOf<City>()

        readableDatabase.rawQuery(QUERY_SELECT_ALL, null).use {
            cursor ->
            while(cursor.moveToNext()){
                val city  = City(cursor.getLong(cursor.getColumnIndex(CITY_KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(CITY_KEY_NAME)))
                Log.i(TAG, "ville = " + city.toString())
                cities.add(city)
            }
        }
        return cities
    }
}