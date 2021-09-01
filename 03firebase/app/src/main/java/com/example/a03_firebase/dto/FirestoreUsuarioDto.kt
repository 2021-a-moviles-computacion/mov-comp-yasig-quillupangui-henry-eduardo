package com.example.a03_firebase.dto

data class FirestoreUsuarioDto(
    var uid: String = "",
    var email: String = "",
    var roles: ArrayList<String> = arrayListOf()
) {
}