package com.example.session5test

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.session5test.Info.idUser
import com.example.session5test.Modules.*
import com.example.session5test.databinding.ActivityChatBinding
import com.google.gson.Gson

class activityChat : AppCompatActivity(), Callback{

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatAdapter
    private lateinit var chat : ModelDataChat




    override fun onCreate(savedInstanceState: Bundle?) {
        Connection.callbacks.add(this)

        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chat = Gson().fromJson(intent.getStringExtra("jsonDataChat"), ModelDataChat::class.java)

        adapter = ChatAdapter(mutableListOf())

        binding.recChat.apply {
            layoutManager = LinearLayoutManager(this@activityChat)
        }

        binding.back.setOnClickListener {
            finish()
        }

        loadHistory()
        fillInfoChat()

        binding.send.setOnClickListener {
            val modelSendMessage = SendMessage(binding.messageText.text.toString(),chat.id,false)
            Connection.client.send(Gson().toJson(modelSendMessage))
            binding.messageText.setText("")
        }

    }
    private fun showToast(message: String){
        runOnUiThread { Toast.makeText(this, message, Toast.LENGTH_LONG).show() }
    }
    private fun loadHistory() {
        Connection.client.send("/chat ${chat.id}")
    }
    private fun fillInfoChat(){
        val modelUser = chat.getOtherUser(idUser)
        binding.fi.text = modelUser.getFI()
        binding.chatCoverText.text = modelUser.lastname[0].toString()
        binding.cardChat.setCardBackgroundColor(modelUser.getColorCard())
    }

    private fun getUser(idUser: Int): ModelUser {
        if (idUser == chat.first.id) return chat.first
        if (idUser == chat.second.id) return chat.second
        throw Exception()
    }

    override fun onOpen() {

    }

    override fun onMessage(modelMessage: ModelMessage) {
        if (modelMessage.idChat == chat.id){
            if (modelMessage.idUser == null){
                showToast(modelMessage.message)
            }else{
                runOnUiThread {
                    val user = getUser(modelMessage.idUser)
                    adapter.addMessage(modelMessage.toModelMessageAdapter(user))
                    binding.recChat.scrollToPosition(0)

                    if (binding.emptyText.visibility == View.VISIBLE)
                        binding.emptyText.visibility == View.GONE
                }
            }
        }

    }

    override fun onChats(chats: List<ModelDataChat>) {
    }

    override fun onChat(chat: ModelChat) {


        val messagesAdapter = chat.messages.map {
            it.toModelMessageAdapter(getUser(it.idUser),it.idUser == idUser)
        }.reversed()
        if (chat.messages.isEmpty()){
            runOnUiThread{
                binding.emptyText.visibility = View.VISIBLE }
        }else{
            runOnUiThread { adapter.setMessages(messagesAdapter)
            binding.emptyText.visibility = View.GONE}
        }
    }

    override fun onPerson(modelUser: ModelUser) {

    }
}


