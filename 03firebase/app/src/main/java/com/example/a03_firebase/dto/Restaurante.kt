package com.example.a03_firebase.dto

class Restaurante(

    var uid:String? = null,
    var nombre:String? = null
) {

    override fun toString(): String {
        return nombre!!
    }
}