package com.example.a03_firebase.dto

class Producto(
    var uid:String = "",
    var nombre:String = "",
    var precio: Double? = null
) {

    override fun toString(): String {
        return "$nombre -> $ ${precio.toString()}"
    }
}