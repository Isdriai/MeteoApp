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

data class DataBase(val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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

        city.id = writableDatabase.insert(CITY_TABLE_NAME, null, values)

        return city.id > 0
    }
}