package com.example.session5test

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.session5test.Modules.*
import com.example.session5test.databinding.ActivityMessengerBinding
import com.google.gson.Gson
import org.java_websocket.client.WebSocketClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.security.auth.callback.Callback


class Messenger : AppCompatActivity(),Callback, com.example.session5test.Callback {

    private lateinit  var adapter:ChatsAdapter
    private lateinit var binding: ActivityMessengerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessengerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter =  ChatsAdapter(onClickChat = object :OnClickChat{
            override fun onClickChat(datachat: ModelDataChat) {
                val intent = Intent(this@Messenger, activityChat::class.java)
                val jsonDataChat = Gson().toJson(datachat)
                intent.putExtra("jsonDataChat", jsonDataChat)
                startActivity(intent)
            }
        })
        

        Connection.client.connect()
        Connection.callbacks.add(this)

        binding.recChats.apply {
            layoutManager = LinearLayoutManager(this@Messenger)
            adapter = this@Messenger.adapter
        }



    }

    override fun onOpen() {
        Connection.client.send("/chats")
    }

    override fun onMessage(modelMeaage: ModelMessage) {}


    override fun onChats(chats: List<ModelDataChat>) {
        runOnUiThread{
            adapter.setData(chats)
        }
    }

    override fun onChat(chat: ModelChat) {

    }


    override fun onPerson(modelUser: ModelUser) {
        binding.nickname.text = modelUser.lastname[0].toString()
        binding.cardView.setCardBackgroundColor(modelUser.getColorCard())
    }
}
fun showAlertDialog(
    context: Context,
    title: String,
    message: String
){
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton("Повторить еще раз", null)
        .show()
}

fun showError(context: Context, error: String){
    showAlertDialog(context, "Ошибка", error)
}