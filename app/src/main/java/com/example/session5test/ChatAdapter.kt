package com.example.session5test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.session5test.Modules.ModelMessageAdapter
import com.example.session5test.Modules.ModelUser.Companion.calcColorAlphaForIdUser

import com.example.session5test.databinding.ItemMessageMyBinding
import com.example.session5test.databinding.ItemMessageOtherBinding

class ChatAdapter(private val messages:MutableList<ModelMessageAdapter>, ): RecyclerView.Adapter<ChatAdapter.ChatHolder>() {
    open class ChatHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        open fun bind(modelMessage:ModelMessageAdapter){}
    }
    class MyVH(private val binding:ItemMessageMyBinding):ChatHolder(binding.root){
        override fun bind(modelMessage: ModelMessageAdapter) {
            binding.message.text = modelMessage.message
            binding.datetime.text = modelMessage.datetime
            binding.backgroundMessage.setBackgroundColor(Info.idUser.calcColorAlphaForIdUser())
        }
    }
    class OtherVH(private val binding: ItemMessageOtherBinding):ChatHolder(binding.root){
        override fun bind(modelMessage: ModelMessageAdapter) {
            binding.message.text = modelMessage.message
            binding.datetime.text = modelMessage.datetime
            binding.backgroundMessage.setBackgroundColor(modelMessage.user.getColorAlphaCard())
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        if (viewType == 0)return MyVH(ItemMessageMyBinding.inflate(LayoutInflater.from(parent.context), parent,false))
        else return OtherVH(ItemMessageOtherBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun getItemViewType(position: Int): Int {
        if (messages[position].isYou)return 0
        else return 1
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {

    }
    fun setData(list: List<ModelMessageAdapter>) {
        messages.clear()
        messages.addAll(list)
    }
    fun setMessages(messages: List<ModelMessageAdapter>){
        this.messages.clear()
        this.messages.addAll(messages)
        notifyDataSetChanged()
    }
    fun addMessage(modelMessage: ModelMessageAdapter){
        messages.add(0, modelMessage)
        notifyItemInserted(0)
    }
}

