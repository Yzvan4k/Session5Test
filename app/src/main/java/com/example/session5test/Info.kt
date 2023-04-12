package com.example.session5test

object Info {
    const val token = "123"
    const val idUser = 6
    val bearerToken:String
        get(){
            return "Bearer $token"
        }
}