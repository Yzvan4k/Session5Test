package com.example.session5test.Modules




data class ModelMessage(
    val message: String,
    val datetime: String,
    val isYou: Boolean,
    val idUser: Int?,
    val idChat: Int,
    val id: Int,
    val isAudio: Boolean,
    val isError: Boolean,
){
    fun toModelMessageAdapter(user: ModelUser): ModelMessageAdapter = ModelMessageAdapter(
        id, message, user, datetime, isYou, isAudio
    )
}

data class ModelMessageChat(
    val id:Int,
    val text: String,
    val idUser: Int,
    val idChat:Int,
    val datetime: String,
    val isAudio: Boolean
){
    fun toModelMessageAdapter(user: ModelUser,isYou: Boolean):ModelMessageAdapter{ return ModelMessageAdapter(id,
        text,user,datetime,isYou,isAudio)
    }


}

data class ModelMessageAdapter(
    val id: Int,
    val message: String,
    val user: ModelUser,
    val datetime: String,
    val isYou: Boolean,
    val isAudio: Boolean,
)
data class SendMessage(
    val text:String,
    val idChat:Int,
    val isAudio: Boolean

)