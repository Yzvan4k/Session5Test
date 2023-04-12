package com.example.session5test

import com.example.session5test.Modules.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.URI

object Connection {


    var callbacks: MutableList<Callback> = mutableListOf()



    const val url = "ws://95.31.130.149:8085/chat"
    val client = object:     WebSocketClient(URI("${url}/chat"), mapOf("idUser" to "6")){
        override fun onOpen(handshakedata: ServerHandshake?) {
            callbacks.forEach{
                it.onOpen()
            }
        }

        override fun onMessage(message: String) {
            if ("\"type\":\"person\"" in message) {
                val modelUser = Gson().fromJson<ModelChatAnswer<ModelUser>>(message, object : TypeToken<ModelChatAnswer<ModelUser>>() {}.type).body
                callbacks.forEach {
                    it.onPerson(modelUser)
                }
            } else if ("\"type\":\"chats\"" in message) {
                val chats = Gson().fromJson<ModelChatAnswer<List<ModelDataChat>>>(message, object : TypeToken<ModelChatAnswer<List<ModelDataChat>>>() {}.type).body
                callbacks.forEach {
                    it.onChats(chats)
                }

            } else if ("\"type\":\"chat\"" in message) {
                val chat = Gson().fromJson<ModelChatAnswer<ModelChat>>(message, object : TypeToken<ModelChatAnswer<ModelChat>>() {}.type).body
                callbacks.forEach {
                    it.onChat(chat)
                }
            } else if ("\"type\":\"message\"" in message) {
                val modelmessage = Gson().fromJson<ModelChatAnswer<ModelMessage>>(message, object : TypeToken<ModelChatAnswer<ModelMessage>>() {}.type).body
                callbacks.forEach {
                    it.onMessage(modelmessage)
                }
            }
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) {

        }

        override fun onError(ex: Exception?) {

        }
    }
}
interface Callback {
    fun onOpen()
    fun onMessage(modelMeaage: ModelMessage)
    fun onChats(chats: List<ModelDataChat>)
    fun onChat(chat: ModelChat)
    fun onPerson(modelUser: ModelUser)

}