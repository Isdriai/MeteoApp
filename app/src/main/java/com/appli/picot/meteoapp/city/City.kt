package com.appli.picot.meteoapp.city

class City(var id: Long, var name: String) {
    constructor(name: String): this(-1, name)
}