package com.example.session5test.Modules

data class ModelDataChat (
    val id:Int,
    val first:ModelUser,
    val second:ModelUser
): java.io.Serializable{
    fun getOtherUser(idUser: Int):ModelUser{
        return if (first.id != idUser) first
        else second
    }
}

