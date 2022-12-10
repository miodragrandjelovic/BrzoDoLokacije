package com.example.pyxiskapri.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.ChatMessage
import com.example.pyxiskapri.utility.UtilityFunctions
import kotlinx.android.synthetic.main.item_chat_message.view.*

class ChattingAdapter(private var messagesList: ArrayList<ChatMessage>, private var context: Context): RecyclerView.Adapter<ChattingAdapter.ChattingViewHolder>() {
    class ChattingViewHolder(friendView: View) : RecyclerView.ViewHolder(friendView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingViewHolder {
        return ChattingViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false) )
    }

    override fun onBindViewHolder(holder: ChattingViewHolder, position: Int) {
        val currentMessage = messagesList[position]

        holder.itemView.apply {
            tv_messageText.text = currentMessage.text
            tv_time.text = currentMessage.time

            if(UtilityFunctions.checkUsernameLogged(currentMessage.sender, context)){
                ll_messageContainer.gravity = Gravity.RIGHT
                tv_messageText.backgroundTintList = this.resources.getColorStateList(R.color.red)
            }
        }
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clearMessages(){
        messagesList.clear()
        notifyDataSetChanged()
    }

    private fun addMessages(messages: ArrayList<ChatMessage>){
        val startingPosition = messagesList.size
        messagesList.addAll(messages)
        notifyItemRangeInserted(startingPosition, messages.size)
    }

    private fun addMessage(message: ChatMessage){
        messagesList.add(message)
        notifyItemInserted(messagesList.size - 1)
    }

}