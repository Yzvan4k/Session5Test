package com.example.session5test.Modules

data class ModelItemChat(
    val id:Int,
    val idChat:Int,
    val datetime:String?,
    val idUser:Int,
)
data class postListChats(
    val list: List<postChats>
)
data class postChats(
    val type:String,
    val body:List<ListBodyChat>
)
data class ListBodyChat(
    val id:Int,
    val first:ModelUser,
    val second:ModelUser
): java.io.Serializable{
    fun getOtherUser(idUser: Int):ModelUser{
        return if (first.id != idUser) first
        else second
    }
}
data class ModelUser(
    val id: Int,
    val firstname:String,
    val lastname:String,
    val patronymic:String,
    val avatar:String
): java.io.Serializable{
    fun getFI():String = "$lastname $firstname"
}
