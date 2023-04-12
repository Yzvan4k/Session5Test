package com.example.session5test.Modules

import android.graphics.Color
import kotlin.math.pow

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
    val firstname: String,
    val lastname: String,
    val patronymic: String,
    val avatar: String?,
    val id: Int
): java.io.Serializable{
    fun getFI(): String = "$lastname $firstname"
    fun getColorCard(): Int = id.calcColorForIdUser()
    fun getColorAlphaCard(): Int = id.calcColorAlphaForIdUser()
    companion object {
        fun Int.calcColorForIdUser(): Int {
            return if (this % 2 != 0) {
                val r = this.toDouble().pow(2.0) % 256
                val g = this.toDouble().pow(3.0) % 256
                val b = this.toDouble().pow(5.0) % 256
                Color.rgb(r.toInt(), g.toInt(), b.toInt())
            }else{
                val r = this.toDouble().pow(11.0) % 256
                val g = this.toDouble().pow(7.0) % 256
                val b = this.toDouble().pow(3.0) % 256
                Color.rgb(r.toInt(), g.toInt(), b.toInt())
            }
        }

        fun Int.calcColorAlphaForIdUser(): Int {
            return if (this % 2 != 0) {
                val r = this.toDouble().pow(2.0) % 256
                val g = this.toDouble().pow(3.0) % 256
                val b = this.toDouble().pow(5.0) % 256
                Color.argb(30, r.toInt(), g.toInt(), b.toInt())
            }else{
                val r = this.toDouble().pow(11.0) % 256
                val g = this.toDouble().pow(7.0) % 256
                val b = this.toDouble().pow(3.0) % 256
                Color.argb(30, r.toInt(), g.toInt(), b.toInt())
            }
        }
    }
}