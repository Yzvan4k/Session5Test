package com.example.session5test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.session5test.Modules.ListBodyChat
import com.example.session5test.Modules.ModelDataChat
import com.example.session5test.Modules.postListChats
import com.example.session5test.databinding.ChatsItemBinding

class ChatsAdapter(private var data:List<ModelDataChat> = listOf(),private val onClickChat:OnClickChat): RecyclerView.Adapter<ChatsAdapter.ChatsHolder>() {
    class ChatsHolder(val chats:ChatsItemBinding) :RecyclerView.ViewHolder(chats.root) {
        fun bind(modelchat:ModelDataChat){
            val chatUser = modelchat.getOtherUser(Info.idUser)
            chats.name.text = chatUser.getFI()
            chats.chatCoverTexr.text = chatUser.lastname[0].toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsHolder {
        return ChatsHolder(ChatsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ChatsHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickChat.onClickChat(data[position])
        }
        holder.bind(data[position])
    }
    fun setData(data:List<ModelDataChat>){
        this.data = data
        notifyDataSetChanged()
    }
}
interface OnClickChat{
    fun onClickChat(datachat:ModelDataChat)
}